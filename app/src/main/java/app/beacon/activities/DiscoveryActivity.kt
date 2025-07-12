package app.beacon.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.beacon.ui.navigators.DiscoveryActivityNavigator
import app.beacon.ui.navigators.MainActivityNavigator
import app.beacon.ui.navigators.MainActivityNavigator
import app.beacon.ui.navigators.SettingsActivityNavigator
import app.beacon.ui.theme.BeaconTheme

class DiscoveryActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BeaconTheme {
                DiscoveryActivityNavigator()
            }
        }
    }
}