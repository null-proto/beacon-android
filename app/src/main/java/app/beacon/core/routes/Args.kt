package app.beacon.core.routes

import android.content.Context
import app.beacon.core.net.types.Bin
import app.beacon.core.net.types.Kv
import app.beacon.state.Session

data class Args(
    val state : Session.State,
    val kv : Kv? = null,
    val data : Bin? = null
)
