package app.beacon.ui.helpers

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import app.beacon.activities.CrashActivity
import app.beacon.state.Globals
import kotlin.system.exitProcess


class CrashHandler(private val context: Context) : Thread.UncaughtExceptionHandler {

//    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(t: Thread, e: Throwable) {
        Handler(Looper.getMainLooper()).post {
            val intent = Intent(context, CrashActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("error", "CRASH!!!")
                putExtra("message", e.message)
                putExtra("s_tree", e.stackTrace.joinToString("\n"))
            }
            context.startActivity(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
//            exitProcess(1)
        }
    }
}
