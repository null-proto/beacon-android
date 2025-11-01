package app.beacon.worker

import android.content.Context
import android.content.ContextParams
import android.content.Intent
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import app.beacon.services.Daemon
import app.beacon.state.Globals
import kotlin.contracts.contract

class StartDaemon(val context: Context ,val params: WorkerParameters) : CoroutineWorker(context , params) {
    override suspend fun doWork(): Result {
        if (!Globals.isCrashed) {
            if (!Globals.isDaemonRunning) {
                context.startService(Intent(context, Daemon::class.java))
            }
            return Result.success()
        }
        return Result.failure()
    }
}