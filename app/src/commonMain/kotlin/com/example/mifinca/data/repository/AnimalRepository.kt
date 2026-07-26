package com.example.mifinca.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.mifinca.data.mapper.toDomain
import com.example.mifinca.database.MiFincaDatabase
import com.example.mifinca.domain.model.Animal
import com.example.mifinca.domain.model.AnimalStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

/**
 * Repositorio para operaciones CRUD de Animales
 */
class AnimalRepository(private val database: MiFincaDatabase) {
    private val queries = database.animalQueries
    
    fun getAnimalById(id: String): Flow<Animal?> {
        return queries.getAnimalById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { it?.toDomain() }
    }
    
    fun getAnimalsByTenant(tenantId: String): Flow<List<Animal>> {
        return queries.getAnimalsByTenant(tenantId)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { animals -> animals.map { it.toDomain() } }
    }
    
    fun getAnimalsByFarm(tenantId: String, farmId: String): Flow<List<Animal>> {
        return queries.getAnimalsByFarm(tenantId, farmId)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { animals -> animals.map { it.toDomain() } }
    }
    
    fun getAnimalsByStatus(tenantId: String, status: AnimalStatus): Flow<List<Animal>> {
        return queries.getAnimalsByStatus(tenantId, status.name)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { animals -> animals.map { it.toDomain() } }
    }
    
    fun searchAnimals(tenantId: String, query: String): Flow<List<Animal>> {
        return queries.searchAnimals(tenantId, query, query)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { animals -> animals.map { it.toDomain() } }
    }
    
    suspend fun insertAnimal(animal: Animal) = withContext(Dispatchers.Default) {
        queries.insertAnimal(
            id = animal.id,
            tenantId = animal.tenantId,
            farmId = animal.farmId,
            visualTag = animal.visualTag,
            rfidTag = animal.rfidTag,
            species = animal.species.name,
            breed = animal.breed,
            sex = animal.sex.name,
            birthDate = animal.birthDate.toString(),
            weightKg = animal.weightKg,
            color = animal.color,
            status = animal.status.name,
            sireId = animal.sireId,
            damId = animal.damId,
            photoUrl = animal.photoUrl,
            notes = animal.notes,
            statusDate = animal.statusDate?.toString(),
            statusNotes = animal.statusNotes,
            syncVersion = animal.syncVersion,
            isDeleted = animal.isDeleted,
            createdAt = animal.createdAt,
            updatedAt = animal.updatedAt
        )
    }
    
    suspend fun updateAnimal(
        id: String,
        farmId: String,
        visualTag: String,
        rfidTag: String?,
        species: String,
        breed: String?,
        sex: String,
        birthDate: String,
        weightKg: Double?,
        color: String?,
        notes: String?
    ) = withContext(Dispatchers.Default) {
        queries.updateAnimal(
            farmId = farmId,
            visualTag = visualTag,
            rfidTag = rfidTag,
            species = species,
            breed = breed,
            sex = sex,
            birthDate = birthDate,
            weightKg = weightKg,
            color = color,
            notes = notes,
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            id = id
        )
    }
    
    suspend fun updateAnimalStatus(
        id: String,
        status: AnimalStatus,
        statusDate: String?,
        statusNotes: String?
    ) = withContext(Dispatchers.Default) {
        queries.updateAnimalStatus(
            status = status.name,
            statusDate = statusDate,
            statusNotes = statusNotes,
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            id = id
        )
    }
    
    suspend fun deleteAnimal(id: String) = withContext(Dispatchers.Default) {
        queries.softDeleteAnimal(
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            id = id
        )
    }
    
    suspend fun getAnimalCount(tenantId: String): Long = withContext(Dispatchers.Default) {
        queries.countAnimalsByTenant(tenantId).executeAsOne()
    }
    
    suspend fun getAnimalCountByFarm(tenantId: String, farmId: String): Long = 
        withContext(Dispatchers.Default) {
            queries.countAnimalsByFarm(tenantId, farmId).executeAsOne()
        }
    
    suspend fun getAnimalCountByStatus(tenantId: String, status: AnimalStatus): Long = 
        withContext(Dispatchers.Default) {
            queries.countAnimalsByStatus(tenantId, status.name).executeAsOne()
        }
}
