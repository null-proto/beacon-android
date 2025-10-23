@file:OptIn(ExperimentalUnsignedTypes::class)

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
import app.beacon.core.routes.Args
import app.beacon.core.routes.Router
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.SocketTimeoutException

class Session(val context: Context , val rt : CoroutineScope) {
    val database = Room.databaseBuilder(context, DataBase::class.java , "registry.db").build()
    val service = Listener()
    val router = Router()

    val state = State(
        registry = database.registry(),
        statelessDB = StatelessDB(),
        context = context
    )

    data class State(
        val registry: Registry,
        val statelessDB: StatelessDB,
        val context: Context
    )

    suspend fun attach(frame: Frame) {
        val kv = frame.getKv()
        if (kv!=null) router.route(Args(state , kv = kv))
        else Log.w("Session" , "failed to route")
    }

    fun start() {
        rt.launch {
            while (true) {
                val client = service.listen();
                Log.i("Listener", "connected with ${client.inetAddress} , ${client.soTimeout}")
                client.soTimeout = Globals.RuntimeConfig.Network.timeout
                rt.launch {
                    try {
                        val fm = client.inputStream.readNBytes(8).map { it.toUByte() }.toUByteArray()
                        val header = Frame.Header.parse(fm)
                        val data = client.inputStream.readNBytes(header.size.toInt())
                        val frame = Frame( header = header, data = data )
                        attach(frame)

                    } catch (e : SocketException) {
                        Log.w("Listener" , "Socket exception : ${e.message}")
                    } catch (e : CancellationException) {
                        Log.w("Listener" , "job cancelled")
                    } catch (e : SocketTimeoutException) {
                        Log.w("Listener" , "Socket Timeout ")
                    } catch (e : Exception) {
                        Log.e("Listener" , "${e.javaClass.name}:: ${e.message}")
                        Log.e("Listener" , e.stackTrace.toString())
                    }
                }
            }
        }
    }

    fun exit() {
        rt.cancel()
    }
}