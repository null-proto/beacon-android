package app.beacon.core.net

import android.util.Log
import app.beacon.state.Globals
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketAddress
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.SocketChannel

class Listener() {
    val socket = ServerSocket(Globals.RuntimeConfig.Network.port , 30 ,InetAddress.getByName(Globals.RuntimeConfig.Network.ip))
    val supervisor = CoroutineScope(Dispatchers.Default + SupervisorJob())
    init {
        socket.soTimeout = Globals.RuntimeConfig.Network.timeout
    }

    fun listen() {
        while (true) {
            val client = socket.accept();
            Log.i("Listener", "connected with ${client.inetAddress} , ${client.soTimeout}")
            supervisor.launch {
                try {
                    val fm = client.inputStream.readNBytes(8).map { it.toUByte() }
                    val size =
                        ((fm[0].toUInt() shl 24) or (fm[1].toUInt() shl 16) or (fm[2].toUInt() shl 8) or (fm[3].toUInt()))
                    val data = client.inputStream.readNBytes(size.toInt())
                    val frame = Frame(
                        size = size,
                        type = ((fm[4].toUInt() shl 24) or (fm[5].toUInt() shl 16) or (fm[6].toUInt() shl 8) or (fm[7].toUInt())),
                        data = data
                    )
                } catch (e : CancellationException) {
                    Log.w("Daemon" , "job cancelled")
                }
            }
        }
    }

    fun close() {
        supervisor.cancel()
    }
}