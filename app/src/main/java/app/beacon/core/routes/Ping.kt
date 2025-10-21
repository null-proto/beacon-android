package app.beacon.core.routes

import android.content.Context
import android.util.Log
import app.beacon.core.PairBox

object Ping : Path {
    override val name: String
        get() = "ping"

    override fun work(context: Context, data: PairBox) {
        Log.d("Router" , "Ping")
    }
}