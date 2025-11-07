package app.beacon.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import app.beacon.state.Discovered.discovered
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.coroutines.cancellation.CancellationException

class Discover: Service() {
    var udpBroadcast: UdpBroadcast? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "START_DISCOVER" -> {
                Thread.sleep(2000)
                udpBroadcast = UdpBroadcast(CoroutineScope(Dispatchers.IO + SupervisorJob()))
            }

            else ->  {
                udpBroadcast?.stop()
            }
        }

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    class UdpBroadcast(val rt : CoroutineScope) {
        private val server = DatagramSocket()
        private val socket = DatagramSocket(18_1920)

        init {
            server.broadcast = true
            socket.broadcast = true
            startServer()
            startReceiver()
        }

        private fun startServer() {
            try {
                rt.launch {
                    val message = DatagramPacket(
                        "hello".toByteArray(),
                        5,
                        InetAddress.getByName("255.255.255.255"),
                        18_1920
                    )
                    Log.d(
                        "Discover",
                        "Sending broadcast message to ${message.address}:${message.port}"
                    )
                    while (!server.isClosed) {
                        server.send(message)
                        delay(2000L)
                    }
                }
            }
            catch (_: CancellationException) {
                Log.d("Discover" , "Stopping broadcast sender")
            }
        }

        private fun startReceiver() {
            try {
                rt.launch {
                    while (!socket.isClosed) {
                        var buf = ByteArray(5)
                        var pkt = DatagramPacket(buf, buf.size)
                        socket.receive(pkt)
                        Log.d("Discover", "Response from ${pkt.address}")
                        if (pkt.address !in discovered) discovered.add(pkt.address)
                    }
                }
            } catch (_: CancellationException) {
                Log.d("Discover" , "Stopping broadcast receiver")
            }
        }

        fun stop() {
            socket.close()
            server.close()
            rt.cancel()
        }

    }
}