package app.beacon.ui.fragments.settings

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import app.beacon.ui.components.option.Item
import app.beacon.ui.components.option.ItemSwitch
import app.beacon.ui.helpers.checkPermission

@Composable fun Permissions() {
    val context = LocalContext.current
    val activity = LocalActivity.current
    var postNotification by remember { mutableStateOf(checkPermission(context , Manifest.permission.POST_NOTIFICATIONS)) }

    Column( modifier = Modifier.verticalScroll(rememberScrollState()) ) {

        Item(
            name = "App info",
            description = "Open system app info for current app",
            onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }
        )

        ItemSwitch(
            name = "Notification",
            description = "Open notification permission",
            toggled = postNotification,
            disabled = true,
            onClick = {
                if (activity != null) {
                    Log.d("PermissionFragment" , "requesting notification permission , perm:$postNotification")
                    if (postNotification) {
                        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                        }
                        context.startActivity(intent)
                    } else {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            0
                        )
                    }

                    postNotification = checkPermission(context , Manifest.permission.POST_NOTIFICATIONS)
                }else {
                    Log.e("PermissionFragment" , "failed to request permission")
                }
            }
        )
    }
}