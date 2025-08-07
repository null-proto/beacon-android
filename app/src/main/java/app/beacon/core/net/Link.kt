package app.beacon.core.net

import app.beacon.core.helpers.Serde
import app.beacon.core.net.packet.Header
import app.beacon.core.net.packet.Packet

interface Link {
    fun send(data: ByteArray)
    fun receive(size: Int) : ByteArray
    fun send(serde: Serde) {
        send(serde.serialize())
    }
}