package com.example.mifinca.presentation.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mifinca.data.remote.AuthApiService
import com.example.mifinca.data.remote.dto.AuthResultDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authApiService: AuthApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onFullNameChange(value: String) = _uiState.update { it.copy(fullName = value, error = null) }
    fun onEmailChange(value: String)    = _uiState.update { it.copy(email = value, error = null) }
    fun onPasswordChange(value: String) = _uiState.update { it.copy(password = value, error = null) }
    fun onFarmNameChange(value: String) = _uiState.update { it.copy(farmName = value, error = null) }

    fun register() {
        val state = _uiState.value

        if (state.fullName.isBlank() || state.email.isBlank() ||
            state.password.isBlank() || state.farmName.isBlank()
        ) {
            _uiState.update { it.copy(error = "Por favor completa todos los campos") }
            return
        }

        if (state.password.length < 8) {
            _uiState.update { it.copy(error = "La contraseña debe tener al menos 8 caracteres") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val response = authApiService.register(
                    fullName = state.fullName.trim(),
                    email    = state.email.trim().lowercase(),
                    password = state.password,
                    farmName = state.farmName.trim()
                )

                if (response.error != null) {
                    _uiState.update {
                        it.copy(isLoading = false, error = mapApiError(response.error.code))
                    }
                    return@launch
                }

                val result = response.data
                if (result != null) {
                    _uiState.update {
                        it.copy(isLoading = false, isRegistered = true, authResult = result)
                    }
                } else {
                    _uiState.update { it.copy(isLoading = false, error = "Respuesta inesperada del servidor") }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "No se pudo conectar al servidor. Verifica tu conexión.")
                }
            }
        }
    }

    fun clearError() = _uiState.update { it.copy(error = null) }

    private fun mapApiError(code: String): String = when (code) {
        "CONFLICT"   -> "Ya existe una cuenta con ese correo"
        "VALIDATION" -> "Datos inválidos. Revisa los campos"
        else         -> "Error al crear la cuenta"
    }
}

data class RegisterUiState(
    val fullName: String    = "",
    val email: String       = "",
    val password: String    = "",
    val farmName: String    = "",
    val isLoading: Boolean  = false,
    val isRegistered: Boolean = false,
    val authResult: AuthResultDto? = null,
    val error: String?      = null
)
