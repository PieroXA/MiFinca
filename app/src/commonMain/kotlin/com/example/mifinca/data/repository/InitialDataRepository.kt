package com.example.mifinca.data.repository

import com.example.mifinca.database.MiFincaDatabase
import com.example.mifinca.domain.model.UserRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

/**
 * Repositorio para inicializar datos de prueba
 */
class InitialDataRepository(private val database: MiFincaDatabase) {
    
    /**
     * Inserta datos iniciales si la base de datos está vacía
     */
    suspend fun initializeDefaultData() = withContext(Dispatchers.Default) {
        // Verificar si ya hay usuarios
        val existingUsers = database.userQueries.getAllUsers().executeAsList()
        
        if (existingUsers.isEmpty()) {
            val now = Clock.System.now().toEpochMilliseconds()
            val tenantId = "tenant_demo_001"
            
            // Crear tenant demo
            database.tenantQueries.insertTenant(
                id = tenantId,
                name = "Finca Demo",
                plan = "basic",
                globalSyncVersion = 0,
                isActive = 1L,
                createdAt = now
            )
            
            // Crear usuario demo
            // Usuario: demo@mifinca.com
            // Contraseña: demo123
            database.userQueries.insertUser(
                id = "user_demo_001",
                tenantId = tenantId,
                email = "demo@mifinca.com",
                passwordHash = "demo123", // En producción usar hashing real
                fullName = "Usuario Demo",
                role = UserRole.OWNER.name,
                isActive = 1L,
                createdAt = now
            )
            
            // Crear una finca demo
            database.farmQueries.insertFarm(
                id = "farm_demo_001",
                tenantId = tenantId,
                name = "Finca Principal",
                address = "Calle Principal #123",
                latitude = 4.7110,
                longitude = -74.0721,
                notes = "Finca de demostración",
                syncVersion = 0,
                isDeleted = 0L,
                createdAt = now,
                updatedAt = now
            )
            
            // Crear animales demo
            val animalIds = listOf("animal_demo_001", "animal_demo_002", "animal_demo_003")
            val tags = listOf("TAG-001", "TAG-002", "TAG-003")
            val species = listOf("BOVINO", "BOVINO", "PORCINO")
            
            animalIds.forEachIndexed { index, id ->
                database.animalQueries.insertAnimal(
                    id = id,
                    tenantId = tenantId,
                    farmId = "farm_demo_001",
                    visualTag = tags[index],
                    rfidTag = null,
                    species = species[index],
                    breed = "Raza Demo",
                    sex = if (index % 2 == 0) "M" else "F",
                    birthDate = "2023-01-15",
                    weightKg = 350.0 + (index * 50),
                    color = "Café",
                    status = "ACTIVE",
                    sireId = null,
                    damId = null,
                    photoUrl = null,
                    notes = "Animal de demostración",
                    statusDate = null,
                    statusNotes = null,
                    syncVersion = 0,
                    isDeleted = 0L,
                    createdAt = now,
                    updatedAt = now
                )
            }
        }
    }
}
