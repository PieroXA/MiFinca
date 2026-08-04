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

class LoginViewModel(
    private val authApiService: AuthApiService
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, error = null) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, error = null) }
    }

    fun login() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password

        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(error = "Por favor completa todos los campos") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            try {
                val response = authApiService.login(
                    email = email,
                    password = password,
                    deviceId = DEVICE_ID
                )

                if (response.error != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = mapApiError(response.error.code)
                        )
                    }
                    return@launch
                }

                val result = response.data
                if (result != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isAuthenticated = true,
                            authResult = result,
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(isLoading = false, error = "Respuesta inesperada del servidor")
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "No se pudo conectar al servidor. Verifica tu conexión."
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    private fun mapApiError(code: String): String = when (code) {
        "UNAUTHORIZED" -> "Correo o contraseña incorrectos"
        "FORBIDDEN"    -> "Cuenta desactivada. Contacta al administrador"
        "NOT_FOUND"    -> "Usuario no encontrado"
        else           -> "Error al iniciar sesión"
    }

    companion object {
        // UUID fijo por dispositivo — en producción usar un ID real del hardware
        const val DEVICE_ID = "00000000-0000-0000-0000-000000000001"
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val authResult: AuthResultDto? = null,
    val error: String? = null
)
