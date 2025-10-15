package app.beacon.core.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import app.beacon.core.database.schema.Pop

@Dao
interface Registry {
    @Insert
    suspend fun addToReg(dev : Pop)

    @Update
    suspend fun updateToReg(dev: Pop)

    @Delete
    suspend fun dropToReg(dev: Pop)

    @Query("SELECT * FROM `open_auth-v2`")
    fun getAllReg() : LiveData<List<Pop>>


    @Query("SELECT * FROM `open_auth-v2` WHERE uuid = :uuid LIMIT 1")
    fun getReg(uuid: String) : LiveData<Pop?>

}