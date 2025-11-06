package app.beacon.modules

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.Outline
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module
import app.beacon.state.Globals
import app.beacon.R

object Notification: Module {

    override val name: String = "notify"

    override suspend fun work(args: Args): Module.Result {

        if (ActivityCompat.checkSelfPermission(
                args.state.context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val notification = NotificationCompat.Builder(
                args.state.context,
                Globals.Notification.OpenChannel.ID
            )
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(args.ip.toString())
                .setContentText(args.kv?.get("msg") ?: "Ping")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setGroup(Globals.Notification.OpenChannel.CATEGORY)
                .build()

            NotificationManagerCompat.from(args.state.context)
                .notify(args.kv?.get("id")?.toInt() ?: args.ip.hashCode(), notification)

            return Module.Result.ok()
        } else {
            return Module.Result.error(code = 10)
        }
    }
}