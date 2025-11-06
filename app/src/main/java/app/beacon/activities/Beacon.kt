package app.beacon.activities

import android.app.Application
import android.content.Intent
import androidx.work.ListenableWorker
import java.util.concurrent.TimeUnit
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import app.beacon.services.Daemon
import app.beacon.state.CallLock
import app.beacon.state.Globals
import app.beacon.state.Session
import app.beacon.ui.helpers.CrashHandler
import app.beacon.worker.StartDaemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.time.Duration

class Beacon : Application() {

    override fun onCreate() {
        Thread.setDefaultUncaughtExceptionHandler(CrashHandler(this))
        super.onCreate()

        Globals.setup(this)

        if (getProcessName().contains(":crash")){
            return
        }

        WorkManager.getInstance(this).enqueue(
            OneTimeWorkRequestBuilder<StartDaemon>()
                .setInitialDelay(5 , TimeUnit.SECONDS )
                .build()
        )

    }
}