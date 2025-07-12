package app.beacon.ui.layouts.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.beacon.ui.components.SettingsItem

@Preview
@Composable fun SettingsLayout(paddingValues: PaddingValues = PaddingValues(0.dp)) {
    Box(
        modifier = Modifier.padding(paddingValues)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items((1..30).toList()) {
                SettingsItem()
            }
        }
    }
}
