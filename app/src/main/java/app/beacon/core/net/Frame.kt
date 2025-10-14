package app.beacon.core.net

import app.beacon.core.PairBox
import app.beacon.core.Serde


@OptIn(ExperimentalUnsignedTypes::class)
data class Frame(
    val size: UInt,
    val type: UInt,
    val data: ByteArray,
) {

    companion object Static {
        fun fromUByteArray(data: UByteArray): Frame {
            return Frame(
                size = data.size.toUInt(),
                type = 1u,
                data = data.map { it.toByte() }.toByteArray()
            )
        }

        fun fromPairBox(data: PairBox): Frame {
            val data = data.serialize()
            return Frame(
                size = data.size.toUInt(),
                type = 2u,
                data = data.map { it.toByte() }.toByteArray()
            )
        }

        fun echo(): Frame {
            return Frame(
                size = 0u,
                type = 0u,
                data = byteArrayOf()
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Frame

        if (size != other.size) return false
        if (type != other.type) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = size.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

}