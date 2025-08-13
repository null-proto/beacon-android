package app.beacon.core.net.tcp

import android.util.Log
import app.beacon.core.net.Link
import app.beacon.core.net.LinkProvider
import app.beacon.core.net.LinkStatus
import app.beacon.core.state.Global
import app.beacon.core.state.Session
import app.beacon.core.state.types.DeviceEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.net.InetSocketAddress
import java.net.ServerSocket

class TcpListener( val port : Int = Global.Defaults.tcpListenerPortMain) : LinkProvider {
    var status = LinkStatus.NotStarted;
    val socket = ServerSocket()
    val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        socket.soTimeout = Global.Defaults.tcpConnectionTimeout
    }

    private fun bind() {
        socket.bind(InetSocketAddress(this.port))
    }

    private fun listen(session: Session) : CoroutineScope {
        scope.launch {
            try {
                while (true) {
                    val link: TcpStream = TcpStream(socket.accept())
                    session.requestHandler(link)
                }
            } catch (e: Exception) {
                Log.e("TcpListener-listen", "${e.cause}: ${e.message}")
            }
        }
        return scope
    }

    override fun start(session: Session) {
        bind()
        if (socket.isBound) {
            status = LinkStatus.Active
            listen(session)
        } else {
            status = LinkStatus.Failed
        }
    }

    override fun stop() {
        socket.close()
        status = if (socket.isClosed) {
            LinkStatus.Dead
        } else {
            LinkStatus.Failed
        }
    }

    override fun createLink(deviceEntry: DeviceEntry): Link {
        return TcpStream.fromSocketAddress(socketAddress = InetSocketAddress(deviceEntry.inetAddress, Global.Defaults.tcpListenerPortMain))
    }
}