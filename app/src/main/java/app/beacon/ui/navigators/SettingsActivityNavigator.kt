package app.beacon.ui.navigators

import android.R.attr.navigationIcon
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.beacon.ui.layouts.HomeLayout
import app.beacon.ui.layouts.ScanLayout
import app.beacon.ui.layouts.settings.SettingsLayout
import app.beacon.ui.theme.BeaconTheme
import app.beacon.ui.theme.LilyScriptOneRegular
import app.beacon.ui.theme.NiconneRegular
import app.beacon.ui.theme.Typography
import java.io.StringReader

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun SettingsActivityNavigator() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val navHostController = rememberNavController()
    val activity = LocalActivity.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = app.beacon.R.string.title_settings),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = Typography.displayMedium.fontSize,
                        style = TextStyle(
                            fontFamily = LilyScriptOneRegular
                        ),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            activity?.finish()
                        },
                        shape = RoundedCornerShape(25)
                    ) {
                        Icon(
                            painter = painterResource(
                                app.beacon.R.drawable.chevron_left
                            ),
                            contentDescription = null
                        )
                    }
                },
                actions = { },
                scrollBehavior = null
            )
        }
    ) { innerPadding ->
//        HomeLayout(innerPadding)
        NavHost(navController = navHostController, startDestination = "home" , modifier = Modifier.padding(innerPadding)) {
            composable("home") {
                SettingsLayout()
            }
        }
    }
}
