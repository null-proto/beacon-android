package app.beacon.modules

import android.util.Log
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module

object NotFound : Module {
    override val name = "test"
    override suspend fun work(args: Args): Module.Result {
        Log.w("NotFound" , "Requested module not found ${args.ip.hostName}-> ${args.kv?.get("route")} ")

        args.kv?.keys()?.forEachIndexed { x, i->
            Log.d("InstTest" , "x -> $i ${args.kv.get(i)}")
        }

        return Module.Result.error(code = 8)
    }
}