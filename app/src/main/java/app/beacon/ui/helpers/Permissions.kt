package app.beacon.ui.helpers

import android.Manifest
import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Environment
import android.provider.Settings
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.Permission


fun checkPermission(context: Context , permission: String ) : Boolean {
        return ContextCompat.checkSelfPermission( context, permission ) == PackageManager.PERMISSION_GRANTED
}


fun checkAndRequestPermission(context: Context ,activity: Activity, permission: String) {
    if (ContextCompat.checkSelfPermission( context, permission ) == PackageManager.PERMISSION_GRANTED)
        ActivityCompat.requestPermissions( activity, arrayOf(permission), 0 )
}

fun requestManageStorage(context: Context , force : Boolean = false) {
    if (!Environment.isExternalStorageManager() || force) {
        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
        context.startActivity(intent)
    }
}

fun requestUsageAccess(context: Context , force: Boolean = false) {
    val appOps = context.getSystemService(AppOpsManager::class.java)
    val mode = appOps.unsafeCheckOpNoThrow(
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        android.os.Process.myUid(),
        context.packageName
    )

    if (mode != AppOpsManager.MODE_ALLOWED || force) {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        context.startActivity(intent)
    }

}