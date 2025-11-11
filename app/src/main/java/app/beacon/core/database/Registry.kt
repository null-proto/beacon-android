package app.beacon.core.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.beacon.core.database.schema.DevInfo
import app.beacon.core.database.schema.ExternalAccess
import app.beacon.core.database.schema.SecreteStore

@Dao
interface Registry {
    @Insert
    suspend fun insert(dev : DevInfo)

    @Insert
    suspend fun insert(sec : SecreteStore)

    @Insert
    suspend fun insert(sec : ExternalAccess)

    @Update
    suspend fun update(dev: DevInfo)

    @Update
    suspend fun update(sec: SecreteStore)

    @Update
    suspend fun update(ext : ExternalAccess)

    @Delete
    suspend fun delete(dev: DevInfo)

    @Delete
    suspend fun delete(sec: SecreteStore)

    @Delete
    suspend fun delete(ext: ExternalAccess)

    @Query("SELECT * FROM `main_table`")
    fun selectAllFromMainTable() : LiveData<List<DevInfo>>

    @Query("SELECT * FROM `main_table` WHERE uuid = :uuid LIMIT 1")
    fun getReg(uuid: String) : LiveData<DevInfo?>

    @Query("SELECT EXISTS(SELECT uuid,lts FROM `secrete_store` WHERE uuid = :uuid AND lts = :lts)")
    fun checkLongTermSecrete(uuid: String , lts : String) : Boolean
}