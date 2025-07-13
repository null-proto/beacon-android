package app.beacon.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import app.beacon.ui.navigators.MainActivityNavigator
import app.beacon.ui.theme.BeaconTheme


class ControlTabActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        setContent {
            BeaconTheme {
                MainActivityNavigator()
            }
        }
    }
}
