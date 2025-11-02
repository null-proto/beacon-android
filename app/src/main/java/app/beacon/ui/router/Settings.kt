package app.beacon.ui.router

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BrokenImage
import androidx.compose.material.icons.rounded.BugReport
import androidx.compose.material.icons.rounded.FormatPaint
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.SettingsEthernet
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import app.beacon.R
import app.beacon.ui.helpers.NavRouter



enum class Settings:NavRouter {

    _Settings,
    Appearance,
    Network,
    Debug;

    override fun getName(): String {
        return when(this) {
            _Settings -> "Settings"
            else -> this.name
        }
    }

    override fun getDescription(context: Context): String {
        return when (this) {
            _Settings -> {
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
            _Settings -> Icons.Rounded.Settings
            Appearance -> Icons.Rounded.FormatPaint
            Debug -> Icons.Rounded.BugReport
            Network -> Icons.Rounded.SettingsEthernet
            else -> Icons.Rounded.BrokenImage
        }
    }

    override fun onClick(context: Context, navHostController: NavHostController?) {
        when (this) {
            _Settings -> {
                // Cannot reach
            }

            else -> {
                navHostController?.navigate(this.name)
            }
        }
    }
}
