package app.beacon.core.database.schema

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import app.beacon.core.PairBox
import app.beacon.core.Serde


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
    val longTermSecrete: String,
): Serde<SecreteStore> {
    override fun serialize(): UByteArray {
        return PairBox().apply {
            put("uuid" , uuid)
            put("long-term-sec" , longTermSecrete)
        }.serialize()
    }

    override fun deserialize(data: UByteArray): SecreteStore? {
        val pb = PairBox(data)
        return SecreteStore(
            uuid = pb.get("uuid") ?: return null,
            longTermSecrete = pb.get("long-term-sec") ?: return null,
        )
    }
}
