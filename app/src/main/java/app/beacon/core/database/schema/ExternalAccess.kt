package app.beacon.core.database.schema

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@OptIn(ExperimentalUnsignedTypes::class)
@Entity(
    tableName = "external_store",
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
data class ExternalAccess (
    @PrimaryKey
    val uuid: String,
    @ColumnInfo(name = "lts")
    val longTermSecrete: String,
)