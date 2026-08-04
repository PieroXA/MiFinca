package com.example.mifinca.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// ── Envelope de respuesta del API ──────────────────────────────────────────────

@Serializable
data class ApiResponse<T>(
    val data: T? = null,
    val error: ApiError? = null
)

@Serializable
data class ApiError(
    val code: String,
    val message: String
)

// ── Auth requests ──────────────────────────────────────────────────────────────

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    @SerialName("device_id") val deviceId: String
)

@Serializable
data class RegisterRequest(
    @SerialName("full_name") val fullName: String,
    val email: String,
    val password: String,
    @SerialName("farm_name") val farmName: String
)

@Serializable
data class RefreshRequest(
    @SerialName("refresh_token") val refreshToken: String
)

// ── Auth responses ─────────────────────────────────────────────────────────────

@Serializable
data class AuthResultDto(
    @SerialName("access_token")      val accessToken: String,
    @SerialName("refresh_token")     val refreshToken: String,
    @SerialName("user_id")           val userId: String,
    @SerialName("tenant_id")         val tenantId: String,
    val role: String,
    @SerialName("last_sync_version") val lastSyncVersion: Long = 0
)
