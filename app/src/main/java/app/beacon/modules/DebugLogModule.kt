package app.beacon.modules

import android.content.Context
import android.util.Log
import app.beacon.core.net.types.Kv
import app.beacon.core.routes.Module

object DebugLogModule : Module {
    override val name = "test"
    override suspend fun work(context: Context, data: Kv) {
        Log.w("Router" , "Test-point route")
    }
}