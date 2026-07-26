package com.example.mifinca.presentation.screen.animal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mifinca.data.repository.AnimalRepository
import com.example.mifinca.domain.model.Animal
import com.example.mifinca.domain.model.AnimalStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para la lista de animales
 */
class AnimalListViewModel(
    private val animalRepository: AnimalRepository,
    private val tenantId: String
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(AnimalListUiState())
    val uiState: StateFlow<AnimalListUiState> = _uiState.asStateFlow()
    
    init {
        loadAnimals()
    }
    
    fun loadAnimals() {
        _uiState.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            try {
                animalRepository.getAnimalsByTenant(tenantId)
                    .collect { animals ->
                        _uiState.update {
                            it.copy(
                                animals = animals,
                                filteredAnimals = filterAnimals(animals, it.searchQuery, it.selectedStatus),
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar animales: ${e.message}"
                    )
                }
            }
        }
    }
    
    fun onSearchQueryChange(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query,
                filteredAnimals = filterAnimals(it.animals, query, it.selectedStatus)
            )
        }
    }
    
    fun onStatusFilterChange(status: AnimalStatus?) {
        _uiState.update {
            it.copy(
                selectedStatus = status,
                filteredAnimals = filterAnimals(it.animals, it.searchQuery, status)
            )
        }
    }
    
    private fun filterAnimals(
        animals: List<Animal>,
        query: String,
        status: AnimalStatus?
    ): List<Animal> {
        var filtered = animals
        
        // Filtrar por búsqueda
        if (query.isNotBlank()) {
            filtered = filtered.filter {
                it.visualTag.contains(query, ignoreCase = true) ||
                it.rfidTag?.contains(query, ignoreCase = true) == true ||
                it.breed?.contains(query, ignoreCase = true) == true
            }
        }
        
        // Filtrar por estado
        if (status != null) {
            filtered = filtered.filter { it.status == status }
        }
        
        return filtered
    }
    
    fun refresh() {
        loadAnimals()
    }
}

/**
 * Estado UI para la lista de animales
 */
data class AnimalListUiState(
    val animals: List<Animal> = emptyList(),
    val filteredAnimals: List<Animal> = emptyList(),
    val searchQuery: String = "",
    val selectedStatus: AnimalStatus? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
