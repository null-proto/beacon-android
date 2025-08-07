package app.beacon.core.net.packet

import android.R.attr.data
import app.beacon.core.helpers.Serde

class Payload(val inner : ByteArray) : Serde{

    override fun serialize(): ByteArray {
        return inner
    }
}