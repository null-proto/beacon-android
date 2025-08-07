package app.beacon.core.net.packet

import app.beacon.core.helpers.Serde

class Packet(
    val header: Serde,
    val payload: Serde? = null
) : Serde {
    companion object Static {
        @JvmStatic fun build(
            type: Header.Type,
            flags: Header.Flags = Header.Flags.from(),
            authentication: ByteArray = byteArrayOf(0,0,0,0),
            payloadId : Int = 0,
            payload: ByteArray? = null,
        ) : Packet {
            val payloadLength = payload?.size
            val checksum = byteArrayOf(0,0,0,0)
            val header = Header.from(
                flags = flags,
                type = type,
                payloadId = payloadId,
                payloadLength = 0,
                authenticationBytes = authentication,
                checksum = checksum
            )
            return Packet(
                header = header!!
            )
        }
    }

    override fun serialize(): ByteArray {
        return if (payload!=null) {
            header.serialize() + payload.serialize()
        } else {
            header.serialize()
        }
    }
}