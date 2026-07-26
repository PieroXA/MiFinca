package com.example.mifinca.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.mifinca.database.MiFincaDatabase

/**
 * Implementación iOS del DatabaseDriverFactory
 */
actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = MiFincaDatabase.Schema,
            name = "mifinca.db"
        )
    }
}
