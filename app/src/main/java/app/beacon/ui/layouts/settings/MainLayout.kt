package app.beacon.ui.layouts.settings

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material.icons.rounded.FormatPaint
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.SettingsEthernet
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.beacon.R
import app.beacon.ui.components.SettingsItem2
import app.beacon.ui.components.option.Item
import app.beacon.ui.helpers.NavOptions



enum class MainLayout: NavOptions {

    Settings,
    Appearance,
    Network,
    Debug;

    override fun getName(): String {
        return this.name
    }

    override fun getDescription(context: Context): String {
        return when (this) {
            Settings -> {
                context.getString(R.string.title_settings)
            }

            Network -> {
                context.getString(R.string.title_settings)
            }

            Appearance -> {
                context.getString(R.string.title_settings)
            }

            Debug -> {
                context.getString(R.string.title_settings)
            }
        }
    }

    override fun getIcon(): ImageVector? {
        return when (this) {
            Settings -> Icons.Rounded.Settings
            Appearance -> Icons.Rounded.FormatPaint
            Debug -> Icons.Rounded.BugReport
            Network -> Icons.Rounded.SettingsEthernet
            else -> Icons.Rounded.BrokenImage
        }
    }

    override fun onClick(context: Context, navHostController: NavHostController?) {
        when (this) {
            Settings -> {
                // Cannot reach
            }
            
            else -> {
                navHostController?.navigate(this.name)
            }
        }
    }

    companion object {
        @JvmStatic
        @Composable
        fun Compose(
            context: Context,
            navOptions: NavOptions = Settings,
            navHostController: NavHostController? = null,
            paddingValues: PaddingValues = PaddingValues(0.dp),
        ) {
            Box(
                modifier = Modifier.padding(paddingValues)
                    .fillMaxSize()
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(MainLayout.entries) {
                        if (it != Settings) {
                            Item(
                                name = it.name,
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
    }
}
