package app.beacon.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import app.beacon.ui.theme.BeaconTheme


class CrashActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val error = intent.getStringExtra("error") ?: "UnSpecified"
        val message = intent.getStringExtra("message") ?: "--"
        val stackTrace = intent.getStringExtra("s_tree") ?: ""

        Log.e("FATAL", "-".repeat(50))
        Log.e("FATAL", "$error: $message")
        Log.e("FATAL", stackTrace)
        Log.e("FATAL", "-".repeat(50))

        setContent {
            BeaconTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("FATAL", fontSize = 20.sp, modifier = Modifier.fillMaxWidth())
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(error)
                        Text(message)
                        Text(stackTrace)
                    }
                }
            }
        }
    }
}