package app.beacon.ui.components.block

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable fun TextBlock(
    text : String ,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    child : @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier.fillMaxWidth()
                .graphicsLayer(alpha = 0.5f),
            textAlign = TextAlign.Justify
        )

        child()
    }
}