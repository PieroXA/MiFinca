package com.example.mifinca.presentation.screen.animal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mifinca.data.repository.AnimalRepository
import com.example.mifinca.data.repository.FarmRepository
import com.example.mifinca.domain.model.Animal
import com.example.mifinca.domain.model.AnimalSpecies
import com.example.mifinca.domain.model.AnimalStatus
import com.example.mifinca.domain.model.Farm
import com.example.mifinca.domain.model.Sex
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate

/**
 * ViewModel para el formulario de animal (crear/editar)
 */
class AnimalFormViewModel(
    private val animalRepository: AnimalRepository,
    private val farmRepository: FarmRepository,
    private val tenantId: String,
    private val animalId: String? = null
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AnimalFormUiState())
    val uiState: StateFlow<AnimalFormUiState> = _uiState.asStateFlow()
    
    init {
        loadFarms()
        animalId?.let { loadAnimal(it) }
    }
    
    private fun loadFarms() {
        viewModelScope.launch {
            farmRepository.getFarmsByTenant(tenantId)
                .collect { farms ->
                    _uiState.update { it.copy(availableFarms = farms) }
                }
        }
    }
    
    private fun loadAnimal(id: String) {
        _uiState.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            animalRepository.getAnimalById(id)
                .collect { animal ->
                    animal?.let {
                        _uiState.update { state ->
                            state.copy(
                                visualTag = it.visualTag,
                                rfidTag = it.rfidTag ?: "",
                                selectedFarmId = it.farmId,
                                selectedSpecies = it.species,
                                breed = it.breed ?: "",
                                selectedSex = it.sex,
                                birthDate = it.birthDate,
                                weightKg = it.weightKg?.toString() ?: "",
                                color = it.color ?: "",
                                notes = it.notes ?: "",
                                isLoading = false,
                                isEditMode = true
                            )
                        }
                    }
                }
        }
    }
    
    fun onVisualTagChange(tag: String) {
        _uiState.update { it.copy(visualTag = tag) }
    }
    
    fun onRfidTagChange(tag: String) {
        _uiState.update { it.copy(rfidTag = tag) }
    }
    
    fun onFarmChange(farmId: String) {
        _uiState.update { it.copy(selectedFarmId = farmId) }
    }
    
    fun onSpeciesChange(species: AnimalSpecies) {
        _uiState.update { it.copy(selectedSpecies = species) }
    }
    
    fun onBreedChange(breed: String) {
        _uiState.update { it.copy(breed = breed) }
    }
    
    fun onSexChange(sex: Sex) {
        _uiState.update { it.copy(selectedSex = sex) }
    }
    
    fun onBirthDateChange(date: LocalDate) {
        _uiState.update { it.copy(birthDate = date) }
    }
    
    fun onWeightChange(weight: String) {
        _uiState.update { it.copy(weightKg = weight) }
    }
    
    fun onColorChange(color: String) {
        _uiState.update { it.copy(color = color) }
    }
    
    fun onNotesChange(notes: String) {
        _uiState.update { it.copy(notes = notes) }
    }
    
    fun saveAnimal() {
        val state = _uiState.value
        
        // Validaciones
        if (state.visualTag.isBlank()) {
            _uiState.update { it.copy(error = "El número de arete es requerido") }
            return
        }
        
        if (state.selectedFarmId.isBlank()) {
            _uiState.update { it.copy(error = "Debes seleccionar una finca") }
            return
        }
        
        _uiState.update { it.copy(isSaving = true, error = null) }
        
        viewModelScope.launch {
            try {
                val now = Clock.System.now().toEpochMilliseconds()
                
                val animal = Animal(
                    id = animalId ?: generateId(),
                    tenantId = tenantId,
                    farmId = state.selectedFarmId,
                    visualTag = state.visualTag,
                    rfidTag = state.rfidTag.takeIf { it.isNotBlank() },
                    species = state.selectedSpecies,
                    breed = state.breed.takeIf { it.isNotBlank() },
                    sex = state.selectedSex,
                    birthDate = state.birthDate,
                    weightKg = state.weightKg.toDoubleOrNull(),
                    color = state.color.takeIf { it.isNotBlank() },
                    status = AnimalStatus.ACTIVE,
                    notes = state.notes.takeIf { it.isNotBlank() },
                    createdAt = animalId?.let { now } ?: now,
                    updatedAt = now
                )
                
                animalRepository.insertAnimal(animal)
                
                _uiState.update { 
                    it.copy(
                        isSaving = false,
                        isSaved = true,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        error = "Error al guardar: ${e.message}"
                    )
                }
            }
        }
    }
    
    private fun generateId(): String {
        return "animal_${Clock.System.now().toEpochMilliseconds()}"
    }
}

/**
 * Estado UI para el formulario de animal
 */
data class AnimalFormUiState(
    val visualTag: String = "",
    val rfidTag: String = "",
    val selectedFarmId: String = "",
    val selectedSpecies: AnimalSpecies = AnimalSpecies.BOVINO,
    val breed: String = "",
    val selectedSex: Sex = Sex.M,
    val birthDate: LocalDate = LocalDate(2024, 1, 1),
    val weightKg: String = "",
    val color: String = "",
    val notes: String = "",
    val availableFarms: List<Farm> = emptyList(),
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val isEditMode: Boolean = false,
    val error: String? = null
)
