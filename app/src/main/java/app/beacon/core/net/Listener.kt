package app.beacon.core.net

import android.util.Log
import app.beacon.core.routes.Args
import app.beacon.core.routes.Router
import app.beacon.state.Globals
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.net.SocketAddress
import java.net.SocketException
import java.net.SocketTimeoutException
import java.nio.channels.AsynchronousServerSocketChannel
import java.nio.channels.SocketChannel

@OptIn(ExperimentalUnsignedTypes::class)
class Listener(address : InetSocketAddress = InetSocketAddress(Globals.RuntimeConfig.Network.ip,0)) {
    val socket = ServerSocket()

    fun listen() : Socket {
        return socket.accept()
    }

    init {
//        socket.soTimeout = Globals.RuntimeConfig.Network.timeout
        socket.reuseAddress = true
        socket.bind(address)

        Log.i("Listener", "Socket binds on ${socket.inetAddress} , ${socket.localPort} ")
        Log.i("Listener", "Socket binds on ${socket.localSocketAddress} // ${socket.inetAddress} ")
    }
}