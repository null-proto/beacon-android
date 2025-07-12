package app.beacon.ui.layouts.settings

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.beacon.R
import app.beacon.ui.components.SettingsItem2
import app.beacon.ui.helpers.NavOptions



enum class MainLayout: NavOptions {

    Settings,
    Debug;

    override fun getName(): String {
        return this.name
    }

    override fun getDescription(context: Context): String {
        return when (this) {
            Settings -> {
                context.getString(R.string.title_settings)
            }

            Debug -> {
                context.getString(R.string.title_settings)
            }
        }
    }

    override fun onClick(context: Context, navHostController: NavHostController?) {
        when (this) {
            Settings -> {
                // Cannot reach
            }

            Debug -> {
                navHostController?.navigate(Debug.name)
            }
        }
    }

    companion object {
        @JvmStatic
        @Preview
        @Composable
        fun Compose(
            navOptions: NavOptions = Settings,
            navHostController: NavHostController? = null,
            paddingValues: PaddingValues = PaddingValues(0.dp),
        ) {
            Box(
                modifier = Modifier.padding(paddingValues)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = 16.dp, bottom = 16.dp)
                ) {
                    items(MainLayout.entries) {
                        if (it!= Settings) {
                            SettingsItem2(
                                navOptions = it,
                                navHostController = navHostController
                            )
                        }
                    }
                }
            }
        }
    }
}
