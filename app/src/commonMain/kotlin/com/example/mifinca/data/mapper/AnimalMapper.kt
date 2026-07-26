package com.example.mifinca.data.mapper

import com.example.mifinca.domain.model.Animal
import com.example.mifinca.domain.model.AnimalSpecies
import com.example.mifinca.domain.model.AnimalStatus
import com.example.mifinca.domain.model.Sex
import kotlinx.datetime.LocalDate
import com.example.mifinca.database.Animal as DbAnimal

/**
 * Mappers para convertir entre entidades de DB y modelos de dominio
 */
fun DbAnimal.toDomain(): Animal {
    return Animal(
        id = id,
        tenantId = tenantId,
        farmId = farmId,
        visualTag = visualTag,
        rfidTag = rfidTag,
        species = AnimalSpecies.fromString(species),
        breed = breed,
        sex = Sex.fromString(sex),
        birthDate = LocalDate.parse(birthDate),
        weightKg = weightKg,
        color = color,
        status = AnimalStatus.fromString(status),
        sireId = sireId,
        damId = damId,
        photoUrl = photoUrl,
        notes = notes,
        statusDate = statusDate?.let { LocalDate.parse(it) },
        statusNotes = statusNotes,
        syncVersion = syncVersion,
        isDeleted = isDeleted != 0L,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Animal.toDbParams(): List<Any?> {
    return listOf(
        id,
        tenantId,
        farmId,
        visualTag,
        rfidTag,
        species.name,
        breed,
        sex.name,
        birthDate.toString(),
        weightKg,
        color,
        status.name,
        sireId,
        damId,
        photoUrl,
        notes,
        statusDate?.toString(),
        statusNotes,
        syncVersion,
        if (isDeleted) 1L else 0L,
        createdAt,
        updatedAt
    )
}
