package app.beacon.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.IBinder
import android.os.VibrationEffect
import android.os.VibratorManager
import android.util.Log
import androidx.core.app.NotificationCompat
import app.beacon.R
import app.beacon.activities.VoIPRX
import app.beacon.state.CallLock
import app.beacon.state.Globals

class VoIPRX: Service() {
    var ringtone: Ringtone? = null
    var vibrator: VibratorManager? = null
    val nid = (0 .. 5000).random().hashCode()
    var title: String? = null
    var name : String? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "STOP_CALL" -> {
                Log.i("VoIP RX" , "Call Ended")
                CallLock.unlock()
                ringtone?.stop()
                vibrator?.defaultVibrator?.cancel()
                stopForeground(STOP_FOREGROUND_REMOVE)
                (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).cancel(nid)

                stopSelf()
            }

            "ATTEND_CALL" -> {
                ringtone?.stop()
                vibrator?.defaultVibrator?.cancel()
                CallLock.lock()
            }

            else -> {
                Log.i("VoIP RX" , "Incoming call")
                title = intent?.getStringExtra("title")
                name = intent?.getStringExtra("name")

                val rx = Intent(this , VoIPRX::class.java).apply {
                    putExtra("title" , title)
                    putExtra("name" , name)
                }

                rx.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP or
                        Intent.FLAG_ACTIVITY_NO_USER_ACTION

                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                ringtone = RingtoneManager.getRingtone(this,uri)
                vibrator =  getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
                ringtone?.isLooping = true
                val vibEffect = VibrationEffect.createWaveform(longArrayOf(200 , 200,0),intArrayOf(0,50,80) , 0)
                startForeground(nid , makeNotification())
                ringtone?.play()
                vibrator?.defaultVibrator?.vibrate(vibEffect)
                startActivity(rx)
            }
        }

        return START_NOT_STICKY
    }

    private fun makeNotification() : Notification {
        val rx = Intent(this , VoIPRX::class.java)
        rx.putExtra("title" , title)
        rx.putExtra("name" , name)

        rx.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_NO_USER_ACTION

        val pending = PendingIntent.getActivity(
            this,0,rx, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val uri = RingtoneManager.getActualDefaultRingtoneUri(this , 1)

        return NotificationCompat.Builder(
            this,
            Globals.Notification.CallChannel.ID
        )
            .setFullScreenIntent(pending ,true)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title?:"Incoming Call")
            .setContentText(name?:"Unknown")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }
}