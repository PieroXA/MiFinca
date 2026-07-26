package com.example.mifinca.data.mapper

import com.example.mifinca.domain.model.Farm
import com.example.mifinca.database.Farm as DbFarm

fun DbFarm.toDomain(): Farm {
    return Farm(
        id = id,
        tenantId = tenantId,
        name = name,
        address = address,
        latitude = latitude,
        longitude = longitude,
        notes = notes,
        syncVersion = syncVersion,
        isDeleted = isDeleted != 0L,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
