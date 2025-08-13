package app.beacon.ui.navigators

import android.content.Intent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import app.beacon.activities.SettingsActivity
import app.beacon.ui.layouts.ScanLayout
import app.beacon.ui.theme.ManufacturingConsentRegular
import app.beacon.ui.theme.Typography
import kotlin.jvm.java

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun DiscoveryActivityNavigator() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val navHostController = rememberNavController()
    val context = LocalContext.current
    val activity = LocalActivity.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
//                        text = stringResource(id = app.beacon.R.string.app_name),
                        text = "Discovery",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = Typography.displaySmall.fontSize,
                        style = TextStyle(
                            fontFamily = ManufacturingConsentRegular
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
                actions = {
                    IconButton(
                        onClick = {
                            val settingsIntent = Intent(context,SettingsActivity::class.java)
                            context.startActivity(settingsIntent)
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                app.beacon.R.drawable.settings2
                            ),
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = null
            )
        }
    ) { innerPadding ->
        ScanLayout(paddingValues = innerPadding)
    }
}
