package app.beacon.core.net

import app.beacon.core.net.types.Kv
import java.net.Socket

class Connection(val socket: Socket) {

    fun sendKv(kv: Kv) {
        val fr = Frame.from(kv)
        socket.outputStream.write(fr.data)
    }

    fun sendBin() {

    }
}