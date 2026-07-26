package com.example.mifinca.data.mapper

import com.example.mifinca.domain.model.User
import com.example.mifinca.domain.model.UserRole
import com.example.mifinca.database.User as DbUser

fun DbUser.toDomain(): User {
    return User(
        id = id,
        tenantId = tenantId,
        email = email,
        fullName = fullName,
        role = UserRole.fromString(role),
        isActive = isActive,
        createdAt = createdAt
    )
}
