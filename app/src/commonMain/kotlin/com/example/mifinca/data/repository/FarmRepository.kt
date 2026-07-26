package com.example.mifinca.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.mifinca.data.mapper.toDomain
import com.example.mifinca.database.MiFincaDatabase
import com.example.mifinca.domain.model.Farm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

/**
 * Repositorio para operaciones CRUD de Fincas
 */
class FarmRepository(private val database: MiFincaDatabase) {
    private val queries = database.farmQueries
    
    fun getFarmById(id: String): Flow<Farm?> {
        return queries.getFarmById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { it?.toDomain() }
    }
    
    fun getFarmsByTenant(tenantId: String): Flow<List<Farm>> {
        return queries.getFarmsByTenant(tenantId)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { farms -> farms.map { it.toDomain() } }
    }
    
    suspend fun insertFarm(farm: Farm) = withContext(Dispatchers.Default) {
        queries.insertFarm(
            id = farm.id,
            tenantId = farm.tenantId,
            name = farm.name,
            address = farm.address,
            latitude = farm.latitude,
            longitude = farm.longitude,
            notes = farm.notes,
            syncVersion = farm.syncVersion,
            isDeleted = farm.isDeleted,
            createdAt = farm.createdAt,
            updatedAt = farm.updatedAt
        )
    }
    
    suspend fun updateFarm(
        id: String,
        name: String,
        address: String?,
        latitude: Double?,
        longitude: Double?,
        notes: String?
    ) = withContext(Dispatchers.Default) {
        queries.updateFarm(
            name = name,
            address = address,
            latitude = latitude,
            longitude = longitude,
            notes = notes,
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            id = id
        )
    }
    
    suspend fun deleteFarm(id: String) = withContext(Dispatchers.Default) {
        queries.softDeleteFarm(
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            id = id
        )
    }
    
    suspend fun getFarmCount(tenantId: String): Long = withContext(Dispatchers.Default) {
        queries.countFarmsByTenant(tenantId).executeAsOne()
    }
}
