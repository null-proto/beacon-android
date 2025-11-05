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
import app.beacon.state.Globals
import kotlin.contracts.contract

object Ring : Module{
    override val name: String = "play-sound"

    override suspend fun work(args: Args): Module.Result {
        Log.d("Ring" , "Playing sound")

//        if (ActivityCompat.checkSelfPermission(
//                args.state.context,
//                Manifest.permission.POST_NOTIFICATIONS
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
////            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
////            val ringtone = RingtoneManager.getRingtone(args.state.context,uri)
////            ringtone.isLooping = true
////
////            val vibrator = args.state.context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
////            val vibEffect = VibrationEffect.createWaveform(longArrayOf(0,800,400,800,400,1000),intArrayOf(0,255,0,255,0,255) , 0)
////
////            ringtone.play()
////            vibrator.defaultVibrator.vibrate(vibEffect)
//
//            val context = args.state.context
//
//            val ringIntent = Intent(context , Ring::class.java)
//            val pending = PendingIntent.getActivity(
//                context,0,ringIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//            )
//            val notification = NotificationCompat.Builder(
//                context,
//                Globals.Notification.CallChannel.ID
//            )
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle(args.ip.toString())
//                .setContentText(args.kv?.get("msg") ?: "Ping")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(NotificationCompat.CATEGORY_CALL)
//                .setFullScreenIntent(pending ,true)
//                .setOngoing(true)
//                .build()
//
//            NotificationManagerCompat.from(context)
//                .notify(args.kv?.get("id")?.toInt() ?: args.ip.hashCode(), notification)
//
//            return Module.Result.ok()
//        } else {
//            return Module.Result.error(code = 10)
//        }


        val intent = Intent(args.state.context , Call::class.java).apply {
            putExtra("title", args.ip.hostName)
            putExtra("name", args.kv?.get("msg"))
        }

        ContextCompat.startForegroundService(args.state.context , intent)
        Log.d("Ring" , "reached foreground service started")

        return Module.Result.ok()
    }
}