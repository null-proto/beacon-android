package app.beacon.ui.layouts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable fun HomeLayout(paddingValues: PaddingValues= PaddingValues(0.dp)) {
    Box(
        modifier = Modifier.padding(paddingValues)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {}
        ) {
            Text("Pair")
        }
    }
}