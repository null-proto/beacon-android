package app.beacon.core.routes

import android.content.Context
import android.util.Log
import app.beacon.core.PairBox

class Router(val context: Context) {
    private var map : HashMap<String , Path> = hashMapOf(
        Pair("test" , object : Path {
            override fun work(context: Context, data: PairBox) {
                Log.w("Router" , "Test-point route")
            }
        })
    );


    fun route(data : PairBox) {
        val key = data.get("route")
        map[key]?.work(context,data)
    }
}