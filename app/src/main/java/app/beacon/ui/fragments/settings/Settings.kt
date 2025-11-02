package app.beacon.ui.fragments.settings

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.beacon.ui.components.option.Item
import app.beacon.ui.helpers.NavRouter
import app.beacon.ui.router.Settings
import app.beacon.ui.router.Settings._Settings



@Composable fun Settings(
    navHostController: NavHostController? = null,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier.padding(paddingValues)
            .fillMaxSize()
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(Settings.entries) {
                if (it != _Settings) {
                    Item(
                        name = it.getName(),
                        description = it.getDescription(context),
                        icon = it.getIcon(),
                        paddingValues = paddingValues,
                        onClick = {
                            it.onClick(context, navHostController)
                        }
                    )
                }
            }
        }
    }
}
