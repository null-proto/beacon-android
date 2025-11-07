package app.beacon.modules

import android.util.Log
import app.beacon.core.request.C
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module

@OptIn(ExperimentalUnsignedTypes::class)
object NotFound : Module {
    override val name = C.NOT_FOUND
    override suspend fun work(args: Args): Module.Result {
        Log.w("NotFound" , "Requested module not found ${args.ip.hostName}-> ${args.kv?.get("route")} ")

        Log.i("NotFound" , "${args.kv?.serialize()?.map { it.toUByte() }}")

        args.kv?.keys()?.forEachIndexed { x, i->
            Log.d("NotFound" , "x -> $i:${args.kv.get(i)}")
        }

        return Module.Result.error(code = 8)
    }
}