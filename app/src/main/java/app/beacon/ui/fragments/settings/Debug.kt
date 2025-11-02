package app.beacon.ui.fragments.settings

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import app.beacon.services.Daemon
import app.beacon.ui.components.option.Item
import app.beacon.worker.StartDaemon
import java.util.concurrent.TimeUnit

@Composable fun Debug(
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier.padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val context = LocalContext.current
        val daemon = Intent(context,Daemon::class.java)

        Item(
            name = "Restart Daemon",
            description = "kill and start daemon again",
            icon = Icons.Rounded.RestartAlt,
            onClick = {
                context.stopService(daemon)
                WorkManager.getInstance(context).enqueue(
                    OneTimeWorkRequestBuilder<StartDaemon>()
                        .setInitialDelay(5 , TimeUnit.SECONDS )
                        .build()
                )

                Toast.makeText(context, "StartDaemon is polled", Toast.LENGTH_SHORT).show()
            }
        )

        Item(
            name = "Start Daemon",
            description = "Start daemon if not running",
            icon = Icons.Rounded.PlayArrow,
            onClick = {
                WorkManager.getInstance(context).enqueue(
                    OneTimeWorkRequestBuilder<StartDaemon>()
                        .setInitialDelay(5 , TimeUnit.SECONDS )
                        .build()
                )

                Toast.makeText(context, "StartDaemon is polled", Toast.LENGTH_SHORT).show()
            }
        )

    }
}