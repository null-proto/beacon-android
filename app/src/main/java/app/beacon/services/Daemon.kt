package app.beacon.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import app.beacon.state.Globals
import app.beacon.state.Globals.Notification
import app.beacon.state.Session
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class Daemon: Service() {
    var supervisorJob = SupervisorJob()
    val serviceScope = CoroutineScope(Dispatchers.IO + supervisorJob )
    lateinit var session : Session
    val nid = (0 .. 5000).random().hashCode()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Services:Daemon:onStartCommand","with startID:$startId")
        serviceScope.launch {
            session.enter()
        }
        return START_STICKY
    }

    override fun onCreate() {
        Log.i("Services:Daemon:onCreate","starting daemon")
        Globals.isDaemonRunning = true
        Globals.daemonForegroundId = nid
        super.onCreate()
        startForeground(nid , makeNotification())
        session = Session( applicationContext , serviceScope )
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.i("Services:Daemon:onDestroy","daemon stopped")
        session.exit()
        Globals.isDaemonRunning = false
        serviceScope.cancel()
        super.onDestroy()
    }

    private fun makeNotification() : android.app.Notification {
        val channel = NotificationChannel(
            Notification.MainChannelDaemon.ID,
            Notification.MainChannelDaemon.NAME,
            NotificationManager.IMPORTANCE_NONE
        )
        val openChannel = NotificationChannel(
            Globals.Notification.OpenChannel.ID,
            Globals.Notification.OpenChannel.CATEGORY,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val callChannel = NotificationChannel(
            Globals.Notification.CallChannel.ID,
            Globals.Notification.CallChannel.CATEGORY,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
        }

        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(channel)
        nm.createNotificationChannel(openChannel)
        nm.createNotificationChannel(callChannel)

        return NotificationCompat.Builder(this , Notification.MainChannelDaemon.ID)
            .setContentTitle(Notification.MainChannelDaemon.TITLE)
            .setContentText(Notification.MainChannelDaemon.DESCRIPTION)
            .setCategory(Notification.MainChannelDaemon.CATEGORY)
            .setGroup(Notification.MainChannelDaemon.CATEGORY)
            .setSilent(true)
            .build()
    }
}