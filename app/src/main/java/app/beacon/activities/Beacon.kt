package app.beacon.activities

import android.app.Application
import app.beacon.ui.helpers.CrashHandler

class Beacon : Application() {
    override fun onCreate() {
        super.onCreate()
        CrashHandler.start(this)
    }
}