package app.beacon.core.routes

import android.content.Context
import app.beacon.core.net.types.Kv
import app.beacon.modules.DebugLogModule

class Router {
    private var map : HashMap<String , Module> = hashMapOf(
        Pair(
            DebugLogModule.name , DebugLogModule
        )
    );


    suspend fun route(args: Args) {
        if (args.kv!=null) {
            val key = args.kv.get("route")
            map[key]?.work(args)
        }
    }
}