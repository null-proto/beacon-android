package app.beacon.core.routes

import app.beacon.core.net.types.Kv
import app.beacon.state.Session
import java.net.InetAddress

data class Args(
    val state : Session.State,
    val kv : Kv,
    val ip : InetAddress,
    val localIp : InetAddress,
    val port : Int,
    val localPort : Int,
    val sts : UInt
)
