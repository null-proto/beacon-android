@file:OptIn(ExperimentalUnsignedTypes::class)

package app.beacon.state

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
//import android.net.TetheringManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import app.beacon.core.database.DataBase
import androidx.room.Room
import app.beacon.core.database.Registry
import app.beacon.core.net.Frame
import app.beacon.core.net.Listener
import app.beacon.core.net.MCast
import app.beacon.core.routes.Args
import app.beacon.core.routes.Router
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.SocketTimeoutException

class Session(val context: Context , val rt : CoroutineScope) {
    val database = Room.databaseBuilder(context, DataBase::class.java , "registry.db").build()
    val service = Listener(InetSocketAddress(Globals.RuntimeConfig.Network.ip,Globals.RuntimeConfig.Network.port))
    val router = Router()

    val state = State(
        registry = database.registry(),
        context = context,
    )

    data class State(
        val registry: Registry,
        val context: Context,
    )

    suspend fun attach(frame: Frame , ip : InetAddress , port : Int , localIp : InetAddress , localPort : Int) : Frame? {
        val kv = frame.getKv()
        val sts = frame.header.secret
        return if (kv!=null)
            router.route(Args(state , kv = kv , ip = ip , port = port , localIp = localIp , localPort = localPort,sts = sts))
        else {
            Log.w("Session", "failed to route")
            null
        }
    }

    fun enter() {
        rt.launch {
            while (true) {
                val client = service.listen();
                Log.i("Listener", "connected with ${client.inetAddress} , ${client.soTimeout}")
                client.soTimeout = Globals.RuntimeConfig.Network.timeout
                rt.launch {
                    try {
                        val port = client.port
                        val localPort = client.localPort
                        val ip = client.inetAddress
                        val localIp = client.localAddress
                        while (!client.isClosed) {
                            val fm =
                                client.inputStream.readNBytes(8).map { it.toUByte() }.toUByteArray()
                            val header = Frame.Header.parse(fm)
                            val data = client.inputStream.readNBytes(header.size.toInt())
                            val frame = Frame(header = header, data = data)

                            val res = attach(frame ,ip , port , localIp , localPort)
                            if (res!=null) client.outputStream.write(res.serialize())
                        }

                    } catch (e : SocketException) {
                        Log.w("Connection" , "Socket exception : ${e.message}")
                    } catch (_ : CancellationException) {
                        Log.w("Connection" , "job cancelled")
                    } catch (_ : SocketTimeoutException) {
                        Log.w("Connection" , "Socket Timeout")
                    } catch (e : Exception) {
                        Log.e("Connection" , "${e.javaClass.name}:: ${e.message}")
                        Log.e("Connection" , e.stackTrace.joinToString("\n"))
                    }
                }
            }
        }
    }

    fun exit() {
        rt.cancel()
    }
}