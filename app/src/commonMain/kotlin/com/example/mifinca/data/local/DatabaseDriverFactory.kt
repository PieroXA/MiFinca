package com.example.mifinca.data.local

import app.cash.sqldelight.db.SqlDriver

/**
 * Factory para crear el driver de SQLDelight según la plataforma
 */
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}
