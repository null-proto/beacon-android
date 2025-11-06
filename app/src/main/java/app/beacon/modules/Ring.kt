package app.beacon.modules

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import app.beacon.R
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module
import app.beacon.services.Call
import app.beacon.state.CallLock
import app.beacon.state.Globals
import kotlin.contracts.contract

object Ring : Module {
    override val name: String = "play-sound"

    override suspend fun work(args: Args): Module.Result {
        Log.d("Ring", "Playing sound")

        if (ActivityCompat.checkSelfPermission(
                args.state.context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (!CallLock.isLocked) {

                CallLock.initiate = true
                val intent = Intent(args.state.context, Call::class.java).apply {
                    putExtra("title", args.ip.hostName)
                    putExtra("name", args.kv?.get("msg"))
                }
                ContextCompat.startForegroundService(args.state.context, intent)
                Log.d("Ring", "reached foreground service started")

                return Module.Result.ok()
            } else {
                return Module.Result.error(code = 11).with { put("msg", "already requested") }
            }
        } else {
            return Module.Result.error(code = 10)
        }
    }
}