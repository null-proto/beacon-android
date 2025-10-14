package app.beacon.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

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
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.i("Services:Daemon:onDestroy","stop")
//        session?.stop()
        super.onDestroy()
    }
}