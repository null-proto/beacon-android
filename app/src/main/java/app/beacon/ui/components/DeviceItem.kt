package app.beacon.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.beacon.ui.theme.Typography


@Preview
@Composable fun DeviceItem(
    name : String = "No device",
    type : String = "Mobile",
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Surface(
        modifier = Modifier.padding(paddingValues)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
//            Icon(
//                painter =
//            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    name,
                    fontSize = Typography.titleSmall.fontSize,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    type,
                    fontSize = Typography.bodySmall.fontSize,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
    }
}