package app.beacon.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import app.beacon.state.Globals.Notification

class Daemon: Service() {
//    var session: Session? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Services:Daemon:onStartCommand","with startID:$startId")
//        Global.isDaemonRunning = true
//        session = Session(this)
        return START_STICKY
    }

    override fun onCreate() {
        Log.i("Services:Daemon:onCreate","start")
        super.onCreate()
        startForeground(1 , makeNotification())
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.i("Services:Daemon:onDestroy","stop")
//        session?.stop()
        super.onDestroy()
    }

    private fun makeNotification() : android.app.Notification {
        val channel = NotificationChannel(
            Notification.MainChannelDaemon.ID,
            Notification.MainChannelDaemon.NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        return NotificationCompat.Builder(this , Notification.MainChannelDaemon.ID)
            .setContentTitle(Notification.MainChannelDaemon.TITLE)
            .setContentText(Notification.MainChannelDaemon.DESCRIPTION)
            .build()
    }
}