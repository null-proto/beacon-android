package app.beacon.core.net.tcp

import app.beacon.core.net.Stream
import java.net.Socket
import java.net.SocketAddress

class TcpStream(socketAddress: SocketAddress) : Stream {
    val socket = Socket()
    init {
        socket.connect(socketAddress)
    }

    override fun send(data: ByteArray) {
        socket.outputStream.apply {
            write(data)
            flush()
        }
    }

    override fun receive(): ByteArray {
        val buffer = ByteArray(1024)
        var acc = ByteArray(0)
        socket.inputStream.apply {
            do {
                val len = read(buffer)
                acc += buffer.slice(0..len)
            } while (len>0)
        }
        return acc
    }
}
