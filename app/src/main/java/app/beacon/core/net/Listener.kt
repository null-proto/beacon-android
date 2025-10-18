package app.beacon.core.net

import android.util.Log
import app.beacon.state.Globals
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketAddress
import java.net.SocketException
import java.net.SocketTimeoutException
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.SocketChannel

@OptIn(ExperimentalUnsignedTypes::class)
class Listener() {
//    val socket = ServerSocket(Globals.RuntimeConfig.Network.port , 30 ,InetAddress.getByName(Globals.RuntimeConfig.Network.ip))
    val socket = ServerSocket(Globals.RuntimeConfig.Network.port)
    val supervisor = CoroutineScope(Dispatchers.Default + SupervisorJob())
    init {
//        socket.soTimeout = Globals.RuntimeConfig.Network.timeout
        Log.i("Listener", "Socket binds on ${socket.inetAddress} , ${socket.localPort} ")
        Log.i("Listener", "Socket binds on ${socket.localSocketAddress} // ${socket.inetAddress} ")
    }

    fun listen() {
        while (true) {
            val client = socket.accept();
            Log.i("Listener", "connected with ${client.inetAddress} , ${client.soTimeout}")
            client.soTimeout = Globals.RuntimeConfig.Network.timeout
            supervisor.launch {
                try {
                    val fm = client.inputStream.readNBytes(8).map { it.toUByte() }.toUByteArray()
                    val header = Frame.Header.parse(fm)
                    val data = client.inputStream.readNBytes(header.size.toInt())
                    val frame = Frame(
                        header = header,
                        data = data
                    )


                } catch (e : SocketException) {
                    Log.w("Listener" , "Socket exception : ${e.message}")
                } catch (e : CancellationException) {
                    Log.w("Listener" , "job cancelled")
                } catch (e : SocketTimeoutException) {
                    Log.w("Listener" , "Socket Timeout ")
                } catch (e : Exception) {
                    Log.e("Listener" , "${e.javaClass.name}:: ${e.message}")
                    Log.e("Listener" , e.stackTrace.toString())
                }
            }
        }
    }

    fun close() {
        supervisor.cancel()
    }
}