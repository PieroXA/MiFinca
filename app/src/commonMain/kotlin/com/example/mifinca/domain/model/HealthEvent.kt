package com.example.mifinca.domain.model

import kotlinx.datetime.LocalDate

/**
 * Modelo de dominio para Evento de Salud
 */
data class HealthEvent(
    val id: String,
    val tenantId: String,
    val animalId: String,
    val eventType: HealthEventType,
    val eventDate: LocalDate,
    val productName: String? = null,
    val doseMl: Double? = null,
    val lotNumber: String? = null,
    val vetName: String? = null,
    val nextDate: LocalDate? = null,
    val notes: String? = null,
    val cost: Double? = null,
    val bulkBatchId: String? = null,
    val syncVersion: Long = 0,
    val isDeleted: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun isUpcoming(currentDate: LocalDate): Boolean {
        return nextDate != null && nextDate >= currentDate
    }
    
    fun isOverdue(currentDate: LocalDate): Boolean {
        return nextDate != null && nextDate < currentDate
    }
}

enum class HealthEventType(val displayName: String) {
    VACCINE("Vacuna"),
    TREATMENT("Tratamiento"),
    DIAGNOSIS("Diagnóstico"),
    DEWORMING("Desparasitación"),
    SURGERY("Cirugía"),
    CHECKUP("Revisión");
    
    companion object {
        fun fromString(value: String): HealthEventType {
            return entries.find { it.name.equals(value, ignoreCase = true) } 
                ?: CHECKUP
        }
    }
}
