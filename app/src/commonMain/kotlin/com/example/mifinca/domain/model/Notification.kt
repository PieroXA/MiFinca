package com.example.mifinca.domain.model

import kotlinx.datetime.LocalDate

/**
 * Modelo de dominio para Notificación
 */
data class Notification(
    val id: String,
    val tenantId: String,
    val healthEventId: String? = null,
    val type: String,
    val message: String,
    val isRead: Boolean = false,
    val scheduledDate: LocalDate,
    val syncVersion: Long = 0,
    val isDeleted: Boolean = false,
    val createdAt: Long,
    val updatedAt: Long
)
