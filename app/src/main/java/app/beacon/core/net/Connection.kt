package app.beacon.core.net

import app.beacon.core.net.types.Kv
import app.beacon.state.Globals
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketAddress

class Connection(address: InetAddress , port: Int) {
    val socket: Socket = Socket()

    init {
        socket.connect(InetSocketAddress(address , port))
//        socket.soTimeout = Globals.RuntimeConfig.Network.timeout
    }

    fun sendKv(kv: Kv) {
        val fr = Frame.from(kv)
        socket.outputStream.write(fr.data)
    }

    fun sendBin() {

    }
}