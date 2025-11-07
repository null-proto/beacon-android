package app.beacon.services

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
import android.os.VibratorManager
import android.util.Log
import androidx.core.app.NotificationCompat
import app.beacon.R
import app.beacon.activities.VoIPTX
import app.beacon.state.CallLock
import app.beacon.state.Globals

class VoIPTX: Service() {
    var ringtone: Ringtone? = null
    var vibrator: VibratorManager? = null
    val nid = (0 .. 5000).random().hashCode()


    override fun onBind(intent: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "STOP_CALL" -> {
                Log.i("Call" , "Call Ended")
                CallLock.unlock()
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

                val tx = Intent(this , VoIPTX::class.java).apply {
                    putExtra("title" , title)
                    putExtra("name" , name)
                }

                tx.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP or
                        Intent.FLAG_ACTIVITY_NO_USER_ACTION

                val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
                ringtone = RingtoneManager.getRingtone(this,uri)
                vibrator =  getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                ringtone?.isLooping = true
                val vibEffect = VibrationEffect.createWaveform(longArrayOf(0,800,400,800,400,1000),intArrayOf(0,255,0,255,0,255) , 0)
                startForeground(nid , makeNotification(title,name))
                ringtone?.play()
                vibrator?.defaultVibrator?.vibrate(vibEffect)
                CallLock.lock()

                startActivity(tx)
            }
        }

        return START_NOT_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    private fun makeNotification(title: String? , name : String?) : Notification {
        val tx = Intent(this , VoIPTX::class.java)
        tx.putExtra("title" , title)
        tx.putExtra("name" , name)

        tx.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_SINGLE_TOP or
                Intent.FLAG_ACTIVITY_NO_USER_ACTION

        val stopCall = Intent(this , app.beacon.services.VoIPTX::class.java).apply {
            action = "STOP_CALL"
        }

        val pending = PendingIntent.getActivity(
            this,0,tx, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
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
            .setContentTitle(title?:"Making Call")
            .setContentText(name?:"Unknown")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
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