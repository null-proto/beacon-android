@file:OptIn(ExperimentalUuidApi::class)

package app.beacon.core.database.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.beacon.core.Serde
import app.beacon.core.net.types.Kv
import kotlin.uuid.ExperimentalUuidApi

@Entity( tableName = "main_table" )
@OptIn(ExperimentalUnsignedTypes::class)
data class DevInfo(
    @PrimaryKey
    val uuid: String,
    val name: String,
    val lastIp: String,
    val createdAt : Long = System.currentTimeMillis(),
)
