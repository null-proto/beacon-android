package app.beacon.core.net.tcp

import app.beacon.core.net.Link
import app.beacon.core.state.Global
import java.net.Socket
import java.net.SocketAddress

class TcpStream(val socket: Socket) : Link {

    override fun send(data: ByteArray) {
        socket.outputStream.apply {
            write(data)
            flush()
        }
    }

    override fun receive(size : Int): ByteArray {
        val buffer = ByteArray(size)
        var acc = ByteArray(0)
        socket.inputStream.apply {
            do {
                val len = read(buffer)
                acc += buffer.slice(0..len)
            } while (len>0)
        }
        return acc
    }

    companion object Static {
        fun fromSocketAddress(socketAddress: SocketAddress): TcpStream {
            val socket = Socket()
            socket.connect(socketAddress)
            socket.soTimeout = Global.Defaults.tcpConnectionTimeout
            return TcpStream(socket)
        }
    }
}
