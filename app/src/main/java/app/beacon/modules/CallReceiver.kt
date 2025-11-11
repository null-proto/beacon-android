package app.beacon.modules

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import app.beacon.core.request.C
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module
import app.beacon.services.VoIPRX
import app.beacon.state.CallLock

object CallReceiver: Module {
    override val name: String = C.CALL_RECEIVER

    override suspend fun work(args: Args): Module.Result {
        Log.d("CallReceiver", "Request received from ${args.ip.hostName}")

        if (ActivityCompat.checkSelfPermission(
                args.state.context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (!CallLock.initiate) {

                CallLock.initiate = true
                val intent = Intent(args.state.context, VoIPRX::class.java).apply {
                    putExtra("title", args.ip.hostName)
                    putExtra("name", args.kv.get(C.MESSAGE))
                }
                ContextCompat.startForegroundService(args.state.context, intent)

                Log.d("CallReceiver", "reached target and forwarding to foreground service rx")
                return Module.Result.ok()
            } else {
                return Module.Result.error(code = 11).with { put(C.MESSAGE, "busy") }
            }
        } else {
            return Module.Result.error(code = 10)
        }
    }

}