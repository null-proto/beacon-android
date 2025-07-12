package app.beacon.ui.components

import android.R.attr.description
import android.R.attr.name
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.beacon.ui.theme.Typography


@Preview
@Composable fun SettingsItem(
    name : String = "Sample Settings",
    description : String = "A detailed description for sample settings",
    paddingValues: PaddingValues = PaddingValues(0.dp),
    @DrawableRes id : Int = app.beacon.R.drawable.settings,
    onClick : ()->Unit = {}
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
                    painter = painterResource(id),
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
                    description,
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
