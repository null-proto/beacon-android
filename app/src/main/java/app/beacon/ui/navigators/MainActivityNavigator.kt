package app.beacon.ui.navigators

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.beacon.ui.layouts.HomeLayout
import app.beacon.ui.layouts.ScanLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun MainActivityNavigator() {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Home"
                    )
                },
                actions = {},
                navigationIcon = {},
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
//        HomeLayout(innerPadding)
        ScanLayout(innerPadding)
    }
}
