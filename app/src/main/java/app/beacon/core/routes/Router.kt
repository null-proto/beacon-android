package app.beacon.core.routes

import app.beacon.core.net.Frame
import app.beacon.core.request.C
import app.beacon.modules.*
import app.beacon.modules.open.Info

class Router {
    suspend fun route(args: Args) : Frame? {
        if (args.kv!=null) {
            return when (args.kv.get(C.ROUTE)) {
                Notification.name -> Notification
                CallReceiver.name -> CallReceiver
                Ring.name -> Ring
                Info.name -> Info

                else -> NotFound
            }.work(args).asFrame()
        }
        return null
    }
}