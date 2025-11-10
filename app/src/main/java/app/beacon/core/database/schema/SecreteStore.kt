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
    indices = [Index("uuid" , unique = true)]
)
data class SecreteStore(
    val uuid: String,
    @PrimaryKey(autoGenerate = true)
    val secID : Int,
    val longTermSecrete: String,
)