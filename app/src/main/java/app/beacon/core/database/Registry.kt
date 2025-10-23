package app.beacon.core.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.beacon.core.database.schema.DevInfo

@Dao
interface Registry {
    @Insert
    suspend fun addToReg(dev : DevInfo)

    @Update
    suspend fun updateToReg(dev: DevInfo)

    @Delete
    suspend fun dropToReg(dev: DevInfo)

    @Query("SELECT * FROM `main_table`")
    fun getAllReg() : LiveData<List<DevInfo>>


    @Query("SELECT * FROM `main_table` WHERE uuid = :uuid LIMIT 1")
    fun getReg(uuid: String) : LiveData<DevInfo?>

}