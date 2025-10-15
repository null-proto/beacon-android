@file:OptIn(ExperimentalUuidApi::class)

package app.beacon.core.database.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity( tableName = "open_auth-v2" )
data class Pop(
    @PrimaryKey
    val uuid: String,
    val name: String,
    val lastIp: String,
)
