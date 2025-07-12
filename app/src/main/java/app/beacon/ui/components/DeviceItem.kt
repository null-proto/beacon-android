package app.beacon.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.beacon.core.Device
import app.beacon.ui.theme.Typography


@Preview
@Composable fun DeviceItem(
    name : String = "No device",
    ipAddress : String = "127.0.0.1",
    type : Device.IntoDevice = Device.DeviceType.Unknown,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onClick:()->Unit = {}
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.padding(paddingValues)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
//                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(type.getIcon()),
                    contentDescription = name,
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 15.dp)
                        .graphicsLayer(alpha = 0.5f)
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    name,
                    fontSize = Typography.titleMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(1.dp)
                )
                Text(
                    ipAddress,
                    fontSize = Typography.bodyMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,

                    modifier = Modifier.graphicsLayer(alpha = 0.5f)
                        .padding(1.dp)
                )
            }
        }
    }
}