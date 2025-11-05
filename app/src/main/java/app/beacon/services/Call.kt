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

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "STOP_CALL" -> {
                Log.i("Call" , "Call Ended")
                ringtone?.stop()
                vibrator?.defaultVibrator?.cancel()
                stopForeground(STOP_FOREGROUND_DETACH)
                stopSelf()
            }

            else -> {
                Log.i("Call" , "Incoming call")
                val title = intent?.getStringExtra("title")
                val name = intent?.getStringExtra("name")

                val ringIntent = Intent(this , Call::class.java).apply {
                    putExtra("title" , title)
                    putExtra("name" , name)
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }

                startActivity(ringIntent)

                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                ringtone = RingtoneManager.getRingtone(this,uri)
                vibrator =  getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                ringtone?.isLooping = true
                val vibEffect = VibrationEffect.createWaveform(longArrayOf(0,800,400,800,400,1000),intArrayOf(0,255,0,255,0,255) , 0)
                ringtone?.play()
                vibrator?.defaultVibrator?.vibrate(vibEffect)
                startForeground(1 , makeNotification(title,name))
            }
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun makeNotification(title: String? , name : String?) : Notification {
        val callChannel = NotificationChannel(
            Globals.Notification.CallChannel.ID,
            Globals.Notification.CallChannel.CATEGORY,
            NotificationManager.IMPORTANCE_HIGH
        )
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(callChannel)

        val ringIntent = Intent(this , Call::class.java)
        ringIntent.putExtra("title" , title)
        ringIntent.putExtra("name" , name)
        ringIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pending = PendingIntent.getActivity(
            this,0,ringIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
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
            .build()

    }
}