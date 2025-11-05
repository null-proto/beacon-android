package app.beacon.core.routes

import android.content.Context
import app.beacon.core.net.Frame
import app.beacon.core.net.types.Kv
import app.beacon.modules.DebugLogModule
import app.beacon.modules.Notification
import app.beacon.modules.Ring

class Router {
    private var map : HashMap<String , Module> = hashMapOf(
        Pair( DebugLogModule.name , DebugLogModule, ),
        Pair( Notification.name , Notification ),
        Pair(Ring.name , Ring ),
    );

    suspend fun route(args: Args) : Frame? {
        if (args.kv!=null) {
            val key = args.kv.get("route")
            return map[key]?.work(args)?.asFrame()
        }
        return null
    }
}