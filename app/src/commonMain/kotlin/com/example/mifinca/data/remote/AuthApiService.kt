package com.example.mifinca.data.remote

import com.example.mifinca.data.remote.dto.ApiResponse
import com.example.mifinca.data.remote.dto.AuthResultDto
import com.example.mifinca.data.remote.dto.LoginRequest
import com.example.mifinca.data.remote.dto.RefreshRequest
import com.example.mifinca.data.remote.dto.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApiService(private val client: HttpClient) {

    suspend fun login(
        email: String,
        password: String,
        deviceId: String
    ): ApiResponse<AuthResultDto> {
        return client.post("$BASE_URL/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(email = email, password = password, deviceId = deviceId))
        }.body()
    }

    suspend fun register(
        fullName: String,
        email: String,
        password: String,
        farmName: String
    ): ApiResponse<AuthResultDto> {
        return client.post("$BASE_URL/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(RegisterRequest(fullName = fullName, email = email, password = password, farmName = farmName))
        }.body()
    }

    suspend fun refresh(refreshToken: String): ApiResponse<AuthResultDto> {
        return client.post("$BASE_URL/auth/refresh") {
            contentType(ContentType.Application.Json)
            setBody(RefreshRequest(refreshToken = refreshToken))
        }.body()
    }
}
