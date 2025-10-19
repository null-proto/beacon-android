package app.beacon.core.net

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.DatagramPacket
import java.net.Inet6Address
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.MulticastSocket
import java.net.NetworkInterface

class MCast {
    val group = InetAddress.getByName("FF15::AE00:AAA1") as Inet6Address
    val port = 4802
    val netInterface = "wlan0"

    val supervisor = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val socket = MulticastSocket(port).apply {
        networkInterface = NetworkInterface.getByName(netInterface)
        joinGroup(InetSocketAddress(group,port) , networkInterface)
    }

    fun listen() {
        supervisor.launch {
            try {
                // TODO : Eww

            } catch (e : CancellationException ) {
                Log.i("MCast", "job cancelled")
            }
        }
    }

    fun close() {
        supervisor.cancel()
    }
}