package app.beacon.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import app.beacon.core.state.Global

class Daemon: Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Services:Daemon:onStartCommand","start")
        Global.isDaemonRunning = true
        return START_STICKY
    }

    override fun onCreate() {
        Log.i("Services:Daemon:onCreate","start")
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.i("Services:Daemon:onDestroy","stop")
        super.onDestroy()
    }
}