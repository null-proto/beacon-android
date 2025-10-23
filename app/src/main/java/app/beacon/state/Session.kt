package app.beacon.state

import android.content.Context
import android.util.Log
import app.beacon.core.database.DataBase
import androidx.room.Room
import androidx.room.RoomDatabase
import app.beacon.core.database.DataBase as AppDataBase
import app.beacon.core.database.Registry
import app.beacon.core.net.Frame
import app.beacon.core.net.Listener
import app.beacon.core.routes.Router
import kotlinx.coroutines.CoroutineScope

class Session(val context: Context , val rt : CoroutineScope) {
    val database = Room.databaseBuilder(context, DataBase::class.java , "registry.db").build()
    val routeService = Listener()
    val router = Router()

    val state = State(
        registry = database.registry(),
        statelessDB = StatelessDB(),
    )

    data class State(
        val registry: Registry,
        val statelessDB: StatelessDB,
    )

    suspend fun attach(frame: Frame) {
        val kv = frame.getKv()
        if (kv!=null) router.route(context,kv)
        else Log.w("Session" , "failed to route")
    }

    fun start() {

    }

    fun stop() {}
}