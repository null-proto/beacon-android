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

    init {
//        socket.soTimeout = Globals.RuntimeConfig.Network.timeout
        Log.i("Listener", "Socket binds on ${socket.inetAddress} , ${socket.localPort} ")
        Log.i("Listener", "Socket binds on ${socket.localSocketAddress} // ${socket.inetAddress} ")
    }

    fun listen() : Socket {
        return socket.accept()
    }
}