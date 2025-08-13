package app.beacon.core.net.packet

import app.beacon.core.helpers.Serde

class Payload(val inner : ByteArray) : Serde{
    override fun serialize(): ByteArray {
        return inner
    }
}