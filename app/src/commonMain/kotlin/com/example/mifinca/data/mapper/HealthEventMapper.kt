package com.example.mifinca.data.mapper

import com.example.mifinca.domain.model.HealthEvent
import com.example.mifinca.domain.model.HealthEventType
import kotlinx.datetime.LocalDate
import com.example.mifinca.database.HealthEvent as DbHealthEvent

fun DbHealthEvent.toDomain(): HealthEvent {
    return HealthEvent(
        id = id,
        tenantId = tenantId,
        animalId = animalId,
        eventType = HealthEventType.fromString(eventType),
        eventDate = LocalDate.parse(eventDate),
        productName = productName,
        doseMl = doseMl,
        lotNumber = lotNumber,
        vetName = vetName,
        nextDate = nextDate?.let { LocalDate.parse(it) },
        notes = notes,
        cost = cost,
        bulkBatchId = bulkBatchId,
        syncVersion = syncVersion,
        isDeleted = isDeleted,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
