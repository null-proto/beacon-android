package app.beacon.core.net

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.net.ServerSocket
import java.net.Socket
import java.nio.channels.AsynchronousServerSocketChannel

class Listener(port: Int = 4800) {
    val socket = ServerSocket(port);
    val supervisor = CoroutineScope(Dispatchers.Default)

    fun listen() {
        while (true) {
            val client = socket.accept();
            val fm = client.inputStream.readNBytes(8).map { it.toUByte() }
            val size = ( (fm[0].toUInt() shl 24) or (fm[1].toUInt() shl 16) or (fm[2].toUInt() shl 8) or (fm[3].toUInt()) )
            val data = client.inputStream.readNBytes(size.toInt())
            val frame = Frame(
                size = size,
                type = ( (fm[4].toUInt() shl 24) or (fm[5].toUInt() shl 16) or (fm[6].toUInt() shl 8) or (fm[7].toUInt()) ),
                data = data
            )
        }
    }
}