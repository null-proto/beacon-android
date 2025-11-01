package app.beacon.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.beacon.ui.navigators.Discovery
import app.beacon.ui.theme.BeaconTheme

class DiscoveryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BeaconTheme {
                Discovery()
            }
        }
    }
}