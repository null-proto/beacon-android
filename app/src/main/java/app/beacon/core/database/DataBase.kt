package app.beacon.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Registry::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun registry() : Registry
}