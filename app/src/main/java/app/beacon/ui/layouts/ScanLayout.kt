package app.beacon.ui.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.beacon.ui.components.DeviceItem
import app.beacon.ui.components.SettingsItem

@Preview
@Composable fun ScanLayout(paddingValues: PaddingValues = PaddingValues(0.dp)) {
    Box(
        modifier = Modifier.padding(paddingValues)
            .fillMaxSize()
    ) {
        Column {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
                    .padding(top=8.dp)
            ) {
                items((1..30).toList()) {
                    DeviceItem()
                }
            }
        }
    }
}