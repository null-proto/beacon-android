package app.beacon.ui.navigators

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.beacon.ui.layouts.settings.DebugLayout
import app.beacon.ui.layouts.settings.MainLayout
import app.beacon.ui.theme.Typography



@OptIn(ExperimentalMaterial3Api::class)
@Composable fun ControlTab() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val navHostController = rememberNavController()
    val activity = LocalActivity.current
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentValue = MainLayout.entries.find { it.name == currentRoute } ?: MainLayout.Settings

    // var currentValue  by remember {  mutableStateOf<MainLayout>(MainLayout.Settings) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = currentValue.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = Typography.displayMedium.fontSize,
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
        NavHost(navController = navHostController, startDestination = MainLayout.Settings.name , modifier = Modifier.padding(innerPadding)) {
            composable(MainLayout.Settings.name) {
                MainLayout.Compose(currentValue, navHostController)
            }
            composable(MainLayout.Debug.name) {
                DebugLayout()
            }
        }
    }
}
