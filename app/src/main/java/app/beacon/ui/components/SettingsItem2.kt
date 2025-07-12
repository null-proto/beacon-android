package app.beacon.ui.components

import androidx.annotation.DrawableRes
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import app.beacon.ui.helpers.NavOptions
import app.beacon.ui.layouts.settings.MainLayout
import app.beacon.ui.theme.Typography


@Preview
@Composable fun SettingsItem2(
    navOptions: NavOptions = MainLayout.Settings,
    navHostController: NavHostController? = null,
    @DrawableRes id : Int = app.beacon.R.drawable.settings,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    val context = LocalContext.current
    Surface(
        onClick = {
            navOptions.onClick(context,navHostController)
        },
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
                    contentDescription = null,
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
                    navOptions.getName(),
                    fontSize = Typography.titleMedium.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(1.dp)
                )
                Text(
                    navOptions.getDescription(context),
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
