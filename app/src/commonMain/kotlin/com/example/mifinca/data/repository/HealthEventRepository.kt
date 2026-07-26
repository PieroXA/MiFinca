package com.example.mifinca.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.mifinca.data.mapper.toDomain
import com.example.mifinca.database.MiFincaDatabase
import com.example.mifinca.database.GetUpcomingHealthEvents as DbUpcomingHealthEvent
import com.example.mifinca.database.HealthEvent as DbHealthEvent
import com.example.mifinca.domain.model.HealthEvent
import com.example.mifinca.domain.model.HealthEventType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

/**
 * Repositorio para operaciones CRUD de Eventos de Salud
 */
class HealthEventRepository(private val database: MiFincaDatabase) {
    private val queries = database.healthEventQueries
    
    fun getHealthEventById(id: String): Flow<HealthEvent?> {
        return queries.getHealthEventById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.Default)
            .map { it?.toDomain() }
    }
    
    fun getHealthEventsByAnimal(tenantId: String, animalId: String): Flow<List<HealthEvent>> {
        return queries.getHealthEventsByAnimal(tenantId, animalId)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { events -> events.map { healthEvent: DbHealthEvent -> healthEvent.toDomain() } }
    }
    
    fun getUpcomingHealthEvents(tenantId: String, fromDate: String): Flow<List<HealthEvent>> {
        return queries.getUpcomingHealthEvents(tenantId, fromDate)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { events -> events.map { healthEvent: DbUpcomingHealthEvent -> healthEvent.toDomain() } }
    }
    
    fun getHealthEventsByType(tenantId: String, eventType: HealthEventType): Flow<List<HealthEvent>> {
        return queries.getHealthEventsByType(tenantId, eventType.name)
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { events -> events.map { healthEvent: DbHealthEvent -> healthEvent.toDomain() } }
    }
    
    suspend fun insertHealthEvent(event: HealthEvent) = withContext(Dispatchers.Default) {
        queries.insertHealthEvent(
            id = event.id,
            tenantId = event.tenantId,
            animalId = event.animalId,
            eventType = event.eventType.name,
            eventDate = event.eventDate.toString(),
            productName = event.productName,
            doseMl = event.doseMl,
            lotNumber = event.lotNumber,
            vetName = event.vetName,
            nextDate = event.nextDate?.toString(),
            notes = event.notes,
            cost = event.cost,
            bulkBatchId = event.bulkBatchId,
            syncVersion = event.syncVersion,
            isDeleted = if (event.isDeleted) 1L else 0L,
            createdAt = event.createdAt,
            updatedAt = event.updatedAt
        )
    }
    
    suspend fun updateHealthEvent(
        id: String,
        eventType: String,
        eventDate: String,
        productName: String?,
        doseMl: Double?,
        lotNumber: String?,
        vetName: String?,
        nextDate: String?,
        notes: String?,
        cost: Double?
    ) = withContext(Dispatchers.Default) {
        queries.updateHealthEvent(
            eventType = eventType,
            eventDate = eventDate,
            productName = productName,
            doseMl = doseMl,
            lotNumber = lotNumber,
            vetName = vetName,
            nextDate = nextDate,
            notes = notes,
            cost = cost,
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            id = id
        )
    }
    
    suspend fun deleteHealthEvent(id: String) = withContext(Dispatchers.Default) {
        queries.softDeleteHealthEvent(
            updatedAt = Clock.System.now().toEpochMilliseconds(),
            id = id
        )
    }
    
    suspend fun getHealthEventCount(tenantId: String, animalId: String): Long = 
        withContext(Dispatchers.Default) {
            queries.countHealthEventsByAnimal(tenantId, animalId).executeAsOne()
        }
}
