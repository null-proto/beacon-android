package app.beacon.modules

import android.content.Context
import android.util.Log
import app.beacon.core.PairBox
import app.beacon.core.routes.Module

object DebugLogModule : Module {
    override val name = "test"
    override suspend fun work(context: Context, data: PairBox) {
        Log.w("Router" , "Test-point route")
    }
}