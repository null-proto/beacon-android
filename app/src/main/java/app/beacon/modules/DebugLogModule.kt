package app.beacon.modules

import android.util.Log
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module

object DebugLogModule : Module {
    override val name = "test"
    override suspend fun work(args: Args): Module.Result {
        Log.w("DebugLogModule" , "Test-route route")

        args.kv?.keys()?.forEachIndexed { x, i->
            Log.d("InstTest" , "x -> $i ${args.kv.get(i)}")
        }

        return Module.Result.ok()
    }
}