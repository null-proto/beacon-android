package app.beacon.core.routes

import android.content.Context
import android.util.Log
import app.beacon.core.PairBox
import kotlin.contracts.contract

class Router(val context: Context) {
    private var map : HashMap<String , Task> = hashMapOf(
        Pair("test" , object : Task {
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