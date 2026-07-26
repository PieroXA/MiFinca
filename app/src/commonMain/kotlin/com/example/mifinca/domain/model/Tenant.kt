package com.example.mifinca.domain.model

/**
 * Modelo de dominio para Tenant (Multi-tenancy)
 */
data class Tenant(
    val id: String,
    val name: String,
    val plan: String = "basic",
    val globalSyncVersion: Long = 0,
    val isActive: Boolean = true,
    val createdAt: Long
)
