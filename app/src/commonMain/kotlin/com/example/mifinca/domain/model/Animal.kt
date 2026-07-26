package com.example.mifinca.domain.model

import kotlinx.datetime.LocalDate

/**
 * Modelo de dominio para Animal
 */
data class Animal(
    val id: String,
    val tenantId: String,
    val farmId: String,
    val visualTag: String,
    val rfidTag: String? = null,
    val species: AnimalSpecies,
    val breed: String? = null,
    val sex: Sex,
    val birthDate: LocalDate,
    val weightKg: Double? = null,
    val color: String? = null,
    val status: AnimalStatus = AnimalStatus.ACTIVE,
    val sireId: String? = null,
    val damId: String? = null,
    val photoUrl: String? = null,
    val notes: String? = null,
    val statusDate: LocalDate? = null,
    val statusNotes: String? = null,
    val syncVersion: Long = 0,
    val isDeleted: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun getAgeInMonths(currentDate: LocalDate): Int {
        val years = currentDate.year - birthDate.year
        val months = currentDate.monthNumber - birthDate.monthNumber
        return years * 12 + months
    }
    
    fun getDisplayName(): String = visualTag
}

enum class AnimalSpecies(val displayName: String) {
    BOVINO("Bovino"),
    OVINO("Ovino"),
    CAPRINO("Caprino"),
    PORCINO("Porcino"),
    EQUINO("Equino");
    
    companion object {
        fun fromString(value: String): AnimalSpecies {
            return entries.find { it.name.equals(value, ignoreCase = true) } 
                ?: BOVINO
        }
    }
}

enum class Sex(val displayName: String) {
    M("Macho"),
    F("Hembra");
    
    companion object {
        fun fromString(value: String): Sex {
            return entries.find { it.name.equals(value, ignoreCase = true) } 
                ?: M
        }
    }
}

enum class AnimalStatus(val displayName: String) {
    ACTIVE("Activo"),
    SOLD("Vendido"),
    DEAD("Muerto"),
    LOST("Perdido");
    
    companion object {
        fun fromString(value: String): AnimalStatus {
            return entries.find { it.name.equals(value, ignoreCase = true) } 
                ?: ACTIVE
        }
    }
}
