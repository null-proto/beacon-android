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
) : Serde<DevInfo> {
    override fun serialize(): UByteArray {
        return Kv().apply {
            put("uuid" , uuid)
            put("name" , name)
            put("last-ip" , lastIp)
        }.serialize()
    }

    override fun deserialize(data: UByteArray): DevInfo? {
        val pb = Kv(data)
        return DevInfo(
            uuid = pb.get("uuid") ?: return null,
            name = pb.get("name") ?: return null,
            lastIp = pb.get("last-ip") ?: return null
        )
    }
}
