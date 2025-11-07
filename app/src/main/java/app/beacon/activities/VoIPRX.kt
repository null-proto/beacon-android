package app.beacon.activities

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.CallEnd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import app.beacon.services.VoIPRX
import app.beacon.state.CallLock
import app.beacon.ui.theme.BeaconTheme

class VoIPRX: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setShowWhenLocked(true)
        setTurnScreenOn(true)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
        )

        WindowCompat.setDecorFitsSystemWindows(window,false)
        WindowInsetsControllerCompat(window,window.decorView).let { ctrlr ->
            ctrlr.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
            ctrlr.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        val title = intent.getStringExtra("title")
        val name = intent.getStringExtra("name")

        val srx = Intent(this , VoIPRX::class.java).apply { action = "STOP_CALL" }
        val arx = Intent(this , VoIPRX::class.java).apply { action = "ATTEND_CALL" }

        setContent {
            BeaconTheme {
                Phone(
                    title = title ,
                    name = name,
                    onAttend = {
                        startForegroundService(arx)
                    },
                    onStop = {
                        startForegroundService(srx)
                        finish()
                    }
                )
            }
        }
    }

    @Preview @Composable private fun Phone(
        name : String? = null,
        title:String? = null,
        onAttend: () -> Unit = {},
        onStop : ()->Unit  = {},
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    title ?: "Anonymous",
                    modifier = Modifier
                        .padding(top = 200.dp),
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    name ?: "(Unknown)",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PreCallButtons(onAttend = onAttend , onStop = onStop)
                Spacer(
                    modifier = Modifier.padding(42.dp)
                )
            }

        }
    }

    @Preview
    @Composable fun PreCallButtons( onAttend: () -> Unit = {}, onStop: () -> Unit = {} ) {
        var isOngoing by remember { CallLock.ongoingCall }

        Row {
            if (!isOngoing) {
                IconButton(
                    onClick = {
                        onAttend()
                        isOngoing = false
                    },
                    colors = IconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                        disabledContentColor = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.5f),
                    ),
                    modifier = Modifier.size(68.dp)
                ) {
                    Icon(Icons.Rounded.Call, contentDescription = null)
                }
                Spacer(modifier = Modifier.padding(horizontal = 58.dp))
            }

            IconButton(
                onClick = onStop,
                colors = IconButtonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                    disabledContainerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                    disabledContentColor = MaterialTheme.colorScheme.onError.copy(alpha = 0.5f),
                ),
                modifier = Modifier.size(68.dp)
            ) {
                Icon(Icons.Rounded.CallEnd, contentDescription = null)
            }
        }
    }
}