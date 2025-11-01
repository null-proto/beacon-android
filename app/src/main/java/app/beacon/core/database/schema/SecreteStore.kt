package app.beacon.core.database.schema

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import app.beacon.core.Serde
import app.beacon.core.net.types.Kv


@OptIn(ExperimentalUnsignedTypes::class)
@Entity(
    tableName = "secrete_store",
    foreignKeys = [
        ForeignKey(
            entity = DevInfo::class,
            parentColumns = ["uuid"],
            childColumns = ["uuid"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("uuid")]
)
data class SecreteStore(
    val uuid: String,
    @PrimaryKey(autoGenerate = true)
    val secID : Int,
    val longTermSecrete: String,
): Serde<SecreteStore> {
    override fun serialize(): UByteArray {
        return Kv().apply {
            put("uuid" , uuid)
            put("long-term-sec" , longTermSecrete)
        }.serialize()
    }

    override fun deserialize(data: UByteArray): SecreteStore? {
        val pb = Kv(data)
        return SecreteStore(
            uuid = pb.get("uuid") ?: return null,
            secID = pb.get("id")?.toInt() ?: return null,
            longTermSecrete = pb.get("long-term-sec") ?: return null,
        )
    }
}
