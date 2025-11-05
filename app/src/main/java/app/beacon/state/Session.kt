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
        return if (kv!=null)
            router.route(Args(state , kv = kv , ip = ip , port = port , localIp = localIp , localPort = localPort))
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
                        Log.e("Connection" , e.stackTrace.toString())
                    }
                }
            }
        }
    }

    fun exit() {
        rt.cancel()
    }

//    fun getActiveNetworkInterface() : NetworkInterface? {
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val iFace = cm.getLinkProperties(cm.activeNetwork)?.interfaceName
//        return NetworkInterface.getByName(iFace)
//    }
//
//    @SuppressLint("DiscouragedPrivateApi")
//    fun getAllAccessPoint() : Array<String> {
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val method = ConnectivityManager::class.java.getDeclaredMethod("getTetheredIfaces")
//        return method.invoke(cm) as Array<String>
//    }
//
//    fun startMulticast() {
//        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val active = cm.activeNetwork ?: return
//        val caps = cm.getNetworkCapabilities(active) ?: return
//        val iFace = cm.getLinkProperties(active)?.interfaceName
//        val network = NetworkInterface.getByName(iFace)
//        Log.i("Session", "Active interface: $iFace")
//
//        when {
//            // ignore cellular network
//            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
//                Log.e("Session", "Active interface \"$iFace\" is Unsupported for multicasting")
//            }
//
//            network.supportsMulticast() -> {
//                Log.w("Session", "Multicast: interface with multicast")
//                state.multicast = MulticastInf(
//                    netIFace = network,
//                    capabilities = caps,
//                    job = rt.launch {
//                        MCast(network).listen()
//                    }
//                )
//            }
//
//            else -> {
//                Log.w("Session", "Multicast: UnSupport")
//            }
//
//        }
//
//    }
//
//    fun stopMulticast() {
//        state.multicast?.job?.cancel()
//        state.multicast = null
//    }
}

