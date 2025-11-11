package app.beacon.core.net

import androidx.annotation.Size
import app.beacon.core.Serde
import app.beacon.core.net.types.Bin
import app.beacon.core.net.types.Kv


@OptIn(ExperimentalUnsignedTypes::class)
data class Frame(
    val header : Header,
    val data: ByteArray,
) {

    // -- header --
    data class Header(
        val size: UInt,
        val type: UInt,
        val secret : UInt = 0u,
    ) {
        companion object {
            fun parse(data: UByteArray): Header? {
                if (data.size<12) return null

                val size =
                    (data[0].toUInt() or (data[1].toUInt() shl 8) or (data[2].toUInt() shl 16) or (data[3].toUInt() shl 24))

                val type =
                    (data[4].toUInt() or (data[5].toUInt() shl 8) or (data[6].toUInt() shl 16) or (data[7].toUInt() shl 24))

                val secret =
                    (data[8].toUInt() or (data[9].toUInt() shl 8) or (data[10].toUInt() shl 16) or (data[11].toUInt() shl 24))

                return Header(
                    size = size,
                    type = type,
                    secret = secret
                )
            }
        }
    }



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Frame

        if (header.size != other.header.size) return false
        if (header.type != other.header.type) return false
        if (header.secret != other.header.secret) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = header.size.hashCode()
        result = 31 * result + header.type.hashCode()
        result = 31 * result + header.secret.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }

    // -- Frame --
    companion object {
        fun from(data: UByteArray , secret: UInt = 0u): Frame {
            return Frame(
                header = Header(
                    size = data.size.toUInt(),
                    type = 1u,
                    secret = secret
                ),
                data = data.map { it.toByte() }.toByteArray()
            )
        }

        fun from(data: Bin, secret: UInt = 0u): Frame {
            val data = data.data
            return Frame(
                header = Header(
                    size = data.size.toUInt(),
                    type = 2u,
                    secret= secret
                ),
                data = data.map { it.toByte() }.toByteArray()
            )
        }

        fun from(data: Kv , secret: UInt = 0u): Frame {
            val data = data.serialize()
            return Frame(
                header = Header(
                    size = data.size.toUInt(),
                    type = 2u,
                    secret = secret
                ),
                data = data.map { it.toByte() }.toByteArray()
            )
        }

        fun from(data: ByteArray , i2u : Boolean = false , secret: UInt = 0u): Frame {
            return Frame(
                header = Header(
                    size = data.size.toUInt(),
                    type = 1u,
                    secret = secret
                ),
                data =  if (i2u) data.map { it.toByte() }.toByteArray() else data
            )
        }

        fun from(secret: UInt = 0u): Frame {
            return Frame(
                header = Header(
                    size = 0u,
                    type = 0u,
                    secret = secret
                ),
                data = byteArrayOf()
            )
        }
    }

    fun getKv() : Kv? {
        return if (header.type==2u) {
            Kv(data = data.map { it.toUByte() }.toUByteArray())
        } else {
            null
        }
    }

    fun getBin() : Bin {
        return Bin(data = data)
    }

    fun serialize() : ByteArray {
        return byteArrayOf(
            (header.size and 0xFFu).toByte(),
            ((header.size shr 8) and 0xFFu).toByte(),
            ((header.size shr 16)and 0xFFu).toByte(),
            ((header.size shr 24)and 0xFFu).toByte(),

            (header.type and 0xFFu).toByte(),
            ((header.type shr 8) and 0xFFu).toByte(),
            ((header.type shr 16) and 0xFFu).toByte(),
            ((header.type shr 24) and 0xFFu).toByte(),

            (header.secret and 0xFFu).toByte(),
            ((header.secret shr 8) and 0xFFu).toByte(),
            ((header.secret shr 16) and 0xFFu).toByte(),
            ((header.secret shr 24) and 0xFFu).toByte()
        ) + data
    }
}