package app.beacon.services

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import app.beacon.R
import app.beacon.activities.Call
import app.beacon.modules.Ring
import app.beacon.state.Globals
import kotlin.text.toInt


class Call:  Service() {
    var ringtone: Ringtone? = null
    var vibrator: VibratorManager? = null
    val nid = (1..10000).random().hashCode()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "STOP_CALL" -> {
                Log.i("Call" , "Call Ended")
                ringtone?.stop()
                vibrator?.defaultVibrator?.cancel()
                stopForeground(STOP_FOREGROUND_REMOVE)

                (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).cancel(nid)

                stopSelf()
            }

            else -> {
                Log.i("Call" , "Incoming call")
                val title = intent?.getStringExtra("title")
                val name = intent?.getStringExtra("name")

                val call = Intent(this , Call::class.java).apply {
                    putExtra("title" , title)
                    putExtra("name" , name)
                }
                call.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

                startActivity(call)

                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                ringtone = RingtoneManager.getRingtone(this,uri)
                vibrator =  getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                ringtone?.isLooping = true
                val vibEffect = VibrationEffect.createWaveform(longArrayOf(0,800,400,800,400,1000),intArrayOf(0,255,0,255,0,255) , 0)
                ringtone?.play()
                vibrator?.defaultVibrator?.vibrate(vibEffect)
                startForeground(nid , makeNotification(title,name))
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun makeNotification(title: String? , name : String?) : Notification {
        val ringIntent = Intent(this , Call::class.java)
        ringIntent.putExtra("title" , title)
        ringIntent.putExtra("name" , name)
        ringIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val stopCall = Intent(this , app.beacon.services.Call::class.java).apply {
            action = "STOP_CALL"
        }

        val pending = PendingIntent.getActivity(
            this,0,ringIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val pendingStopCall = PendingIntent.getActivity(
            this,0,stopCall, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(
            this,
            Globals.Notification.CallChannel.ID
        )
            .setFullScreenIntent(pending ,true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title?:"Incoming Call")
            .setContentText(name?:"Ping")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setOngoing(true)
            .setSilent(true)
            .addAction(
                android.R.drawable.ic_menu_close_clear_cancel,
                "Decline",
                pendingStopCall
            )
            .build()

    }
}