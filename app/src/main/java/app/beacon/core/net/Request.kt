package app.beacon.core.net

import app.beacon.core.net.types.Kv
import java.net.InetAddress
import java.net.Socket

data class Request(
    val socket: Socket,
    val kv : Kv
) {
    fun getIp() : InetAddress {
        return socket.inetAddress
    }

    fun get(k : String) : String? {
        return kv.get(k)
    }
}
