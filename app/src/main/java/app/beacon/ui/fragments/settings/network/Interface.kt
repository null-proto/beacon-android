package app.beacon.ui.fragments.settings.network

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.beacon.ui.components.block.TextBlock

@Composable fun Interface() {
    TextBlock( "Select available interfaces to use" )

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

    }
}