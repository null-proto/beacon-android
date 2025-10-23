package app.beacon.modules

import android.util.Log
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module

object DebugLogModule : Module {
    override val name = "test"
    override suspend fun work(args: Args) {
        Log.w("Router" , "Test-point route")
    }
}