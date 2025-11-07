package app.beacon.core.routes

import app.beacon.core.net.Frame
import app.beacon.modules.CallReceiver
import app.beacon.modules.NotFound
import app.beacon.modules.Notification
import app.beacon.modules.Ring

class Router {
    suspend fun route(args: Args) : Frame? {
        if (args.kv!=null) {
            return when (args.kv.get("route")) {
                Notification.name -> Notification
                Ring.name -> Ring
                CallReceiver.name -> CallReceiver

                else -> NotFound
            }.work(args).asFrame()
        }
        return null
    }
}