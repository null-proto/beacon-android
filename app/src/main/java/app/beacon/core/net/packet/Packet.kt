package app.beacon.core.net.packet

import app.beacon.core.helpers.Serde

class Packet(
    val header: Header,
    val metadata: Metadata? = null,
    val payload: Serde? = null
) : Serde {


    override fun serialize(): ByteArray {
        var ser = header.serialize()
        if (metadata!=null) {
            ser+=metadata.serialize()
        }
        if (payload!=null) {
            ser+=payload.serialize()
        }
        return ser
    }
}