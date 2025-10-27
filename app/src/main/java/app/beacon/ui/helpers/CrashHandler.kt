package app.beacon.ui.helpers

import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import app.beacon.state.Globals
import kotlin.system.exitProcess


class CrashHandler(private val context: Context) : Thread.UncaughtExceptionHandler {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            if (!Globals.isCrashHandlerRunning) {
                Globals.isCrashHandlerRunning = true
                Thread.setDefaultUncaughtExceptionHandler(CrashHandler(context))
            }
        }
    }

    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

    override fun uncaughtException(t: Thread, e: Throwable) {
        Handler(Looper.getMainLooper()).post {
            AlertDialog.Builder(context)
                .setTitle("MainThread Crashed: ${e.message}")
                .setMessage(e.toString())
                .setCancelable(false)
                .setPositiveButton("Close") { _, _ ->
                    android.os.Process.killProcess(android.os.Process.myPid())
                    exitProcess(1)
                }
                .show()
        }
        defaultHandler?.uncaughtException(t, e)
    }
}
