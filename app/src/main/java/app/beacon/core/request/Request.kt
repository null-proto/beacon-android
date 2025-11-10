package app.beacon.core.request

import app.beacon.core.net.Frame
import app.beacon.core.net.types.Bin
import app.beacon.core.net.types.Kv
import app.beacon.state.Globals
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException

@OptIn(ExperimentalUnsignedTypes::class)
class Request(address: InetAddress, port: Int) {
    val socket: Socket = Socket()

    init {
        socket.connect(InetSocketAddress(address, port))
        socket.soTimeout = Globals.RuntimeConfig.Network.timeout
    }

    fun sendKv(kv: Kv) {
        val fr = Frame.from(kv)
        socket.outputStream.write(fr.data)
    }

    fun sendBin(bin: Bin) {
        val fr = Frame.from(bin)
        socket.outputStream.write(fr.data)
    }

    fun send(data: ByteArray) {
        val fr = Frame.from(data, i2u = true)
        socket.outputStream.write(fr.data)
    }

    fun receive() : Frame? {
        try {
            val fm = socket.inputStream.readNBytes(8).map { it.toUByte() }.toUByteArray()
            val header = Frame.Header.parse(fm)
            val data = socket.inputStream.readNBytes(header.size.toInt())
            return Frame(header = header, data = data)
        } catch (e : SocketTimeoutException) {
            return null
        }
    }
}