package app.beacon.state

import androidx.compose.runtime.mutableStateListOf
import java.net.InetAddress

object Discovered {
    var discovered = mutableStateListOf<InetAddress>()
}