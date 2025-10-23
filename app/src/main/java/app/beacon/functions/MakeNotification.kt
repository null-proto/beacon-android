package app.beacon.functions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import app.beacon.core.database.schema.DevInfo
import app.beacon.functions.MakeNotification.Args

class MakeNotification : Task<Args> {
    data class Args(
        val title : String,
        val message : String,
        val whois : DevInfo
    )

    override fun run(context: Context, arg: Args) {
        // TODO: Create channel
        val notify = NotificationCompat.Builder(context, arg.whois.name)
            .setContentTitle(arg.title)
            .setContentText(arg.message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ContextCompat.checkSelfPermission(context , Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(context).notify(arg.message.hashCode() , notify)
        } else {
            Log.e("MakeNotification" , "permission denied : ${arg.whois.name} - ${arg.title} - ${arg.message}")
        }
    }
}