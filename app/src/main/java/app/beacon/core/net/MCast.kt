package app.beacon.core.net

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.net.Inet6Address
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.MulticastSocket
import java.net.NetworkInterface

class MCast {
    val group = InetAddress.getByName("ff02::1") as Inet6Address
    val port = 4802
    val netInterface = "wlan0"

    val supervisor = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val socket = MulticastSocket(port).apply {
        networkInterface = NetworkInterface.getByName(netInterface)
        joinGroup(InetSocketAddress(group,port) , networkInterface)
    }

    fun listen() {
    }

}