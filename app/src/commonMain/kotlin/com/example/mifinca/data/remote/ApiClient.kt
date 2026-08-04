package com.example.mifinca.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * URL base del backend.
 * Android usa 10.0.2.2 para acceder a localhost del host desde el emulador.
 * En dispositivo físico cambiar por la IP local de la máquina (ej: 192.168.x.x).
 */
const val BASE_URL = "http://10.0.2.2:8080/api/v1"

fun createHttpClient(): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = false
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("[Ktor] $message")
                }
            }
            level = LogLevel.BODY
        }
    }
}
