package app.beacon.core.routes

import android.content.Context
import app.beacon.core.PairBox
import app.beacon.modules.DebugLogModule

class Router(val context: Context) {
    private var map : HashMap<String , Module> = hashMapOf(
        Pair(
            DebugLogModule.name , DebugLogModule
        )
    );


    suspend fun route(data : PairBox) {
        val key = data.get("route")
        map[key]?.work(context,data)
    }
}