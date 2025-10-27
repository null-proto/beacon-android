package app.beacon.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import app.beacon.services.Daemon
import app.beacon.state.Globals
import app.beacon.ui.helpers.CrashHandler
import app.beacon.ui.navigators.MainActivityNavigator
import app.beacon.ui.theme.BeaconTheme

class MainActivity : ComponentActivity() {
    fun startDaemon() {
        if (!Globals.isDaemonRunning) {
            this.startService(Intent(this, Daemon::class.java))
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        Globals.setup(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startDaemon()

        setContent {
            BeaconTheme {
                MainActivityNavigator()
            }
        }
    }
}