package app.beacon.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.beacon.core.database.schema.DevInfo
import app.beacon.core.database.schema.ExternalAccess
import app.beacon.core.database.schema.SecreteStore

@Database(entities = [ DevInfo::class , SecreteStore::class , ExternalAccess::class ], version = 2)
abstract class DataBase : RoomDatabase() {
    abstract fun registry() : Registry
}