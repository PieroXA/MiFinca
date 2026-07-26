package com.example.mifinca.domain.model

/**
 * Modelo de dominio para Finca
 */
data class Farm(
    val id: String,
    val tenantId: String,
    val name: String,
    val address: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val notes: String? = null,
    val syncVersion: Long = 0,
    val isDeleted: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun hasLocation(): Boolean = latitude != null && longitude != null
    
    fun getCoordinates(): Pair<Double, Double>? {
        return if (latitude != null && longitude != null) {
            Pair(latitude, longitude)
        } else null
    }
}
