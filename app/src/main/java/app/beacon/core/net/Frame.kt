package app.beacon.core.net

import androidx.annotation.Size
import app.beacon.core.PairBox
import app.beacon.core.Serde


@OptIn(ExperimentalUnsignedTypes::class)
data class Frame(
    val header : Header,
    val data: ByteArray,
) {

    data class Header(
        val size: UInt,
        val type: UInt
    ) {
        companion object Static {
            fun parse(data : UByteArray) : Header {
                val size =
                    ((data[0].toUInt() shl 24) or (data[1].toUInt() shl 16) or (data[2].toUInt() shl 8) or (data[3].toUInt()))
                val type =
                    ((data[4].toUInt() shl 24) or (data[5].toUInt() shl 16) or (data[6].toUInt() shl 8) or (data[7].toUInt()))
                return Header(
                    size = size,
                    type = type
                )
            }
        }
    }

    companion object Static {
        fun fromUByteArray(data: UByteArray): Frame {
            return Frame(
                header = Header(
                    size = data.size.toUInt(),
                    type = 1u,
                ),
                data = data.map { it.toByte() }.toByteArray()
            )
        }

        fun fromPairBox(data: PairBox): Frame {
            val data = data.serialize()
            return Frame(
                header = Header(
                    size = data.size.toUInt(),
                    type = 2u,
                ),
                data = data.map { it.toByte() }.toByteArray()
            )
        }

        fun echo(): Frame {
            return Frame(
                header = Header(
                    size = 0u,
                    type = 0u,
                ),
                data = byteArrayOf()
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Frame

        if (header.size != other.header.size) return false
        if (header.type != other.header.type) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = header.size.hashCode()
        result = 31 * result + header.type.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

}