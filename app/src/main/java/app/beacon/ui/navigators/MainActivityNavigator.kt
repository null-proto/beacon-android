package app.beacon.ui.navigators

import android.R.attr.maxLines
import android.R.attr.navigationIcon
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.platform.LocalContext
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
import app.beacon.activities.DiscoveryActivity
import app.beacon.activities.SettingsActivity
import app.beacon.ui.layouts.HomeLayout
import app.beacon.ui.layouts.ScanLayout
import app.beacon.ui.theme.BeaconTheme
import app.beacon.ui.theme.LilyScriptOneRegular
import app.beacon.ui.theme.NiconneRegular
import app.beacon.ui.theme.Typography
import kotlinx.coroutines.launch
import java.io.StringReader
import kotlin.jvm.java

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun MainActivityNavigator() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val navHostController = rememberNavController()
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent =  {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.padding(42.dp))
                    Text(
                        text = stringResource(id = app.beacon.R.string.app_name),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = Typography.displayMedium.fontSize,
                        style = TextStyle(
                            fontFamily = LilyScriptOneRegular
                        )
                    )
                    Spacer(Modifier.padding(8.dp))
                    HorizontalDivider()
                    Spacer(Modifier.padding(8.dp))
                    NavigationDrawerItem(
                        label = {
                            Text("Pair")
                        },
                        selected = false,
                        onClick = {
                            val discoveryIntent = Intent(context, DiscoveryActivity::class.java)
                            context.startActivity(discoveryIntent)
                        }
                    )
                    NavigationDrawerItem(
                        label = {
                            Text("Settings")
                        },
                        selected = false,
                        onClick = {
                            val settingsIntent = Intent(context, SettingsActivity::class.java)
                            context.startActivity(settingsIntent)
                        }
                    )
                    Spacer(Modifier.padding(10.dp))
                    HorizontalDivider()
                    Text("Paired", modifier = Modifier.padding(6.dp))
                    Spacer(Modifier.padding(8.dp))


                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                MediumTopAppBar(
                    title = {
                        Text(
                            text = "Home",
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
                                scope.launch {
                                    if (drawerState.isOpen)
                                        drawerState.close()
                                    else
                                        drawerState.open()
                                }
                            },
                            shape = RoundedCornerShape(25)
                        ) {
                            Icon(
                                painter = painterResource(
                                    app.beacon.R.drawable.chart_no_axes_gantt
                                ),
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                val discoveryIntent = Intent(context, DiscoveryActivity::class.java)
                                context.startActivity(discoveryIntent)
                            }
                        ) {
                            Icon(
                                painter = painterResource(
                                    app.beacon.R.drawable.radar
                                ),
                                contentDescription = null
                            )
                        }
                    },
                    scrollBehavior = null
                )
            }
        ) { innerPadding ->
//        HomeLayout(innerPadding)

            NavHost(
                navController = navHostController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    HomeLayout()
                }
            }

        }
    }
}
