package app.beacon.activities

import android.app.Application
import android.content.Intent
import app.beacon.services.Daemon
import app.beacon.state.Globals
import app.beacon.state.Session
import app.beacon.ui.helpers.CrashHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class Beacon : Application() {
    fun startDaemon() {
        if (!Globals.isDaemonRunning) {
            this.startService(Intent(this, Daemon::class.java))
        }
    }

    override fun onCreate() {
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler(this))
        Globals.setup(this)
        startDaemon()

        var rt = CoroutineScope(Dispatchers.IO)
        val session = Session(context = this, rt = rt)

        super.onCreate()
    }
}