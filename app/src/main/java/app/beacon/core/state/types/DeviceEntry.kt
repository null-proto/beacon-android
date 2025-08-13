package app.beacon.core.state.types

import android.util.Log
import app.beacon.core.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.InetAddress
import java.net.SocketTimeoutException
import java.util.concurrent.ConcurrentLinkedQueue

data class DeviceEntry(
    var name: String,
    var inetAddress: InetAddress,
    val publicInfo: Device.DevicePublicInfo,
    val fullInfo: Device.DeviceFullInfo?
) {
    val coroutineScope = CoroutineScope(Dispatchers.Default)

    val jobList = ConcurrentLinkedQueue<Job>()

    fun post(closure : suspend (DeviceEntry)->Unit ) {
        coroutineScope.launch {
            try {
                closure(this@DeviceEntry)
                Log.v("CONNECTION-POST-$name" , "Success: Job completed")
            } catch (e: SocketTimeoutException) {
                Log.e("CONNECTION-POST-$name" , "Failed: socket timeout exception")
            } catch (e: IOException) {
                Log.e("CONNECTION-POST-$name" , "Failed: broken IO")
            } catch (e: Exception) {
                Log.e("CONNECTION-POST-$name" , "${e.cause}: ${e.message}")
            }
        }
    }

    fun <T> postJob(closure: suspend (DeviceEntry)->T ) : Deferred<T?> {
        val job = coroutineScope.async {
            try {
                closure(this@DeviceEntry)
            } catch (e: SocketTimeoutException) {
                Log.e("CONNECTION-POST-JOB-$name" , "Failed: socket timeout exception")
                null
            } catch (e: IOException) {
                Log.e("CONNECTION-POST-JOB-$name" , "Failed: broken IO")
                null
            } catch (e : Exception) {
                Log.e("CONNECTION-POST-JOB-$name" , "${e.cause}: ${e.message}")
                null
            }
        }.apply {
            invokeOnCompletion {
                Log.v("CONNECTION-POST-JOB-$name" , "Success: Job completed")
                jobList.remove(this)
            }
        }
        Log.v("CONNECTION-POST-JOB-$name" , "Job posted ( ${jobList.size} active )")
        jobList += job
        return job
    }

    companion object Static {
        fun fromJson(obj : JSONObject) : DeviceEntry? {
            try {
                val publicInfo: Device.DevicePublicInfo = obj.getJSONObject("publicInfo").let {
                    Device.DevicePublicInfo(
                        name = it.getString("name"),
                        type = Device.DeviceType.valueOf(it.getString("type")),
                        hostname = it.getString("hostname")
                    )
                }
                val fullInfo: Device.DeviceFullInfo = obj.getJSONObject("privateInfo").let {
                    Device.DeviceFullInfo(
                        name = it.getString("name"),
                        type = Device.DeviceType.valueOf(it.getString("type")),
                        hostname = it.getString("hostname")
                    )
                }

                return DeviceEntry(
                    name = obj.getString("name"),
                    inetAddress = InetAddress.getByName(obj.getString("inetAddress")),
                    publicInfo = publicInfo,
                    fullInfo = fullInfo
                )
            } catch (e : JSONException) {
                return null
            }
        }
    }

    override fun toString(): String {
        return """
            {
              "name":"${inetAddress.hostName}",
              "inetAddress" : "${inetAddress.hostAddress?:inetAddress.hostName}"
            }
        """.trimIndent()
    }
}
