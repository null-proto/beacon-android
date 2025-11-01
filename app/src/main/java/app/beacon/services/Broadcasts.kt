package app.beacon.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Broadcasts : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val daemon = Intent(context, Daemon::class.java)

        when (intent?.action) {
//            Intent.ACTION_RUN ,
            Intent.ACTION_PACKAGE_CHANGED ,
                -> context?.startService(daemon)
        }
    }
}