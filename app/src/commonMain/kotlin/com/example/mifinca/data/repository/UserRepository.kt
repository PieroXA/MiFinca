package com.example.mifinca.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.mifinca.data.mapper.toDomain
import com.example.mifinca.database.MiFincaDatabase
import com.example.mifinca.domain.model.User
import com.example.mifinca.domain.model.UserRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

/**
 * Repositorio para operaciones CRUD de Usuarios
 */
class UserRepository(private val database: MiFincaDatabase) {
    private val queries = database.userQueries
    
    fun getUserById(id: String): Flow<User?> {
        return queries.getUserById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { it?.toDomain() }
    }
    
    fun getUserByEmail(email: String): Flow<User?> {
        return queries.getUserByEmail(email)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { it?.toDomain() }
    }
    
    fun getUsersByTenant(tenantId: String): Flow<List<User>> {
        return queries.getUsersByTenant(tenantId)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { users -> users.map { it.toDomain() } }
    }
    
    suspend fun insertUser(
        id: String,
        tenantId: String,
        email: String,
        passwordHash: String,
        fullName: String,
        role: UserRole,
        isActive: Boolean = true,
        createdAt: Long
    ) = withContext(Dispatchers.Default) {
        queries.insertUser(
            id = id,
            tenantId = tenantId,
            email = email,
            passwordHash = passwordHash,
            fullName = fullName,
            role = role.name,
            isActive = if (isActive) 1L else 0L,
            createdAt = createdAt
        )
    }
    
    suspend fun updateUser(
        id: String,
        fullName: String,
        role: UserRole,
        isActive: Boolean
    ) = withContext(Dispatchers.Default) {
        queries.updateUser(
            fullName = fullName,
            role = role.name,
            isActive = if (isActive) 1L else 0L,
            id = id
        )
    }
    
    suspend fun deleteUser(id: String) = withContext(Dispatchers.Default) {
        queries.deleteUser(id)
    }
    
    suspend fun validateCredentials(email: String, password: String): User? = 
        withContext(Dispatchers.Default) {
            val normalizedEmail = email.trim().lowercase()
            val user = queries.getUserByEmail(normalizedEmail).executeAsOneOrNull()
            // En producción, aquí deberías usar bcrypt o similar
            if (user != null && user.passwordHash == hashPassword(password)) {
                user.toDomain()
            } else {
                null
            }
        }
    
    // Función simple de hashing (en producción usar bcrypt)
    private fun hashPassword(password: String): String {
        // Por ahora solo retornamos el password (en producción usar hashing real)
        return password
    }
}
