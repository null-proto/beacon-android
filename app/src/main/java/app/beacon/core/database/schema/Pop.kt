@file:OptIn(ExperimentalUuidApi::class)

package app.beacon.core.database.schema

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.beacon.core.PairBox
import app.beacon.core.Serde
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Entity( tableName = "open_auth-v2" )
@OptIn(ExperimentalUnsignedTypes::class)
data class Pop(
    @PrimaryKey
    val uuid: String,
    val name: String,
    val lastIp: String,
) : Serde<Pop> {
    override fun serialize(): UByteArray {
        return PairBox().apply {
            put("uuid" , uuid)
            put("name" , name)
            put("last-ip" , lastIp)
        }.serialize()
    }

    override fun deserialize(data: UByteArray): Pop? {
        val pb = PairBox(data)
        return Pop(
            uuid = pb.get("uuid") ?: return null,
            name = pb.get("name") ?: return null,
            lastIp = pb.get("last-ip") ?: return null
        )
    }
}
