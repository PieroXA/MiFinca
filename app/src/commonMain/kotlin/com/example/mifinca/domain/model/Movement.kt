package com.example.mifinca.domain.model

import kotlinx.datetime.LocalDate

/**
 * Modelo de dominio para Movimiento de Animal
 */
data class Movement(
    val id: String,
    val tenantId: String,
    val animalId: String,
    val fromFarmId: String? = null,
    val toFarmId: String? = null,
    val movementDate: LocalDate,
    val reason: MovementReason,
    val notes: String? = null,
    val syncVersion: Long = 0,
    val isDeleted: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)

enum class MovementReason(val displayName: String) {
    SALE_TRANSFER("Venta/Transferencia"),
    GRAZING("Pastoreo"),
    VETERINARY("Veterinario"),
    RETURN("Regreso"),
    PURCHASE("Compra"),
    OTHER("Otro");
    
    companion object {
        fun fromString(value: String): MovementReason {
            return entries.find { it.name.equals(value, ignoreCase = true) } 
                ?: OTHER
        }
    }
}
