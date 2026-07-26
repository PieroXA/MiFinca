package com.example.mifinca.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.mifinca.database.MiFincaDatabase

/**
 * Implementación Android del DatabaseDriverFactory
 */
actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = MiFincaDatabase.Schema,
            context = context,
            name = "mifinca.db"
        )
    }
}
