package com.example.mifinca.presentation.screen.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mifinca.data.repository.AnimalRepository
import com.example.mifinca.data.repository.FarmRepository
import com.example.mifinca.data.repository.HealthEventRepository
import com.example.mifinca.domain.model.AnimalStatus
import com.example.mifinca.domain.model.HealthEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

/**
 * ViewModel para el Dashboard
 */
class DashboardViewModel(
    private val animalRepository: AnimalRepository,
    private val farmRepository: FarmRepository,
    private val healthEventRepository: HealthEventRepository,
    private val tenantId: String
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadDashboardData()
    }
    
    fun loadDashboardData() {
        _uiState.update { it.copy(isLoading = true) }
        
        viewModelScope.launch {
            try {
                // Cargar contadores
                val totalAnimals = animalRepository.getAnimalCount(tenantId)
                val activeAnimals = animalRepository.getAnimalCountByStatus(tenantId, AnimalStatus.ACTIVE)
                val totalFarms = farmRepository.getFarmCount(tenantId)
                
                // Cargar eventos de salud próximos
                val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
                
                healthEventRepository.getUpcomingHealthEvents(tenantId, today.toString())
                    .collect { events ->
                        _uiState.update {
                            it.copy(
                                totalAnimals = totalAnimals.toInt(),
                                activeAnimals = activeAnimals.toInt(),
                                totalFarms = totalFarms.toInt(),
                                upcomingEvents = events,
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar datos: ${e.message}"
                    )
                }
            }
        }
    }
    
    fun refresh() {
        loadDashboardData()
    }
}

/**
 * Estado UI para el Dashboard
 */
data class DashboardUiState(
    val totalAnimals: Int = 0,
    val activeAnimals: Int = 0,
    val totalFarms: Int = 0,
    val upcomingEvents: List<HealthEvent> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
