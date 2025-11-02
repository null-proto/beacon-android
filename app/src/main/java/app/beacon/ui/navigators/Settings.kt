package app.beacon.ui.navigators

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.beacon.ui.fragments.settings.Appearance
import app.beacon.ui.fragments.settings.Debug
import app.beacon.ui.fragments.settings.Network
import app.beacon.ui.router.Settings
import app.beacon.ui.theme.Typography
import app.beacon.ui.fragments.settings.Settings as SettingsFrag



@OptIn(ExperimentalMaterial3Api::class)
@Composable fun Settings() {
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val navHostController = rememberNavController()
    val activity = LocalActivity.current
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentValue = Settings.entries.find { it.name == currentRoute } ?: Settings._Settings

    // var currentValue  by remember {  mutableStateOf<MainLayout>(MainLayout.Settings) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = currentValue.getName(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = Typography.displaySmall.fontSize,
                        style = TextStyle(
                            fontFamily = FontFamily.Default
                        ),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (!navHostController.popBackStack()) {
                                activity?.finish()
                            }
                        },
                        shape = RoundedCornerShape(25)
                    ) {
                        Icon( Icons.AutoMirrored.Filled.ArrowBack , contentDescription = null )
                    }
                },
                actions = { },
                scrollBehavior = null,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    ) { innerPadding ->
//        HomeLayout(innerPadding)
        NavHost(navController = navHostController, startDestination = Settings._Settings.name , modifier = Modifier.padding(innerPadding)) {

            composable(Settings._Settings.name) { SettingsFrag(navHostController) }
            composable(Settings.Debug.name) { Debug() }
            composable(Settings.Appearance.name) { Appearance() }
            composable(Settings.Network.name) { Network()}

        }
    }
}
