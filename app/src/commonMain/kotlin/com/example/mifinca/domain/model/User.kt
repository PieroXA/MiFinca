package com.example.mifinca.domain.model

/**
 * Modelo de dominio para Usuario
 */
data class User(
    val id: String,
    val tenantId: String,
    val email: String,
    val fullName: String,
    val role: UserRole,
    val isActive: Boolean = true,
    val createdAt: Long
) {
    fun canManageAnimals(): Boolean = role in listOf(UserRole.OWNER, UserRole.VET)
    
    fun canManageFarms(): Boolean = role == UserRole.OWNER
    
    fun canViewReports(): Boolean = role in listOf(UserRole.OWNER, UserRole.VET)
}

enum class UserRole(val displayName: String) {
    OWNER("Propietario"),
    VET("Veterinario"),
    WORKER("Trabajador");
    
    companion object {
        fun fromString(value: String): UserRole {
            return entries.find { it.name.equals(value, ignoreCase = true) } 
                ?: WORKER
        }
    }
}
