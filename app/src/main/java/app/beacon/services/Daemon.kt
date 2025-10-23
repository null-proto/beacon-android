package app.beacon.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import app.beacon.core.net.Listener
import app.beacon.state.Globals
import app.beacon.state.Globals.Notification
import app.beacon.state.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Daemon: Service() {
    var rt = CoroutineScope(Dispatchers.IO)
    val session = Session(context = this, rt = rt)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Services:Daemon:onStartCommand","with startID:$startId")
        session.start()
        return START_STICKY
    }

    override fun onCreate() {
        Log.i("Services:Daemon:onCreate","start")
        super.onCreate()
        startForeground(1 , makeNotification())
        Globals.isDaemonRunning = true
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.i("Services:Daemon:onDestroy","stop")
        session.exit()
        super.onDestroy()
    }

    private fun makeNotification() : android.app.Notification {
        val channel = NotificationChannel(
            Notification.MainChannelDaemon.ID,
            Notification.MainChannelDaemon.NAME,
            NotificationManager.IMPORTANCE_NONE
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        return NotificationCompat.Builder(this , Notification.MainChannelDaemon.ID)
            .setContentTitle(Notification.MainChannelDaemon.TITLE)
            .setContentText(Notification.MainChannelDaemon.DESCRIPTION)
            .setCategory(Notification.MainChannelDaemon.CATEGORY)
            .setGroup(Notification.MainChannelDaemon.CATEGORY)
            .build()
    }
}