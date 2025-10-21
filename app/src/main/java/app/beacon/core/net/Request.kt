package app.beacon.core.net

import app.beacon.core.PairBox
import java.net.InetAddress
import java.net.Socket

data class Request(
    val socket: Socket,
    val pairBox: PairBox
) {
    fun getIp() : InetAddress {
        return socket.inetAddress
    }

    fun get(k : String) : String? {
        return pairBox.get(k)
    }
}
