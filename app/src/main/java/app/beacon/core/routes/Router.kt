package app.beacon.core.routes

import app.beacon.core.net.Frame
import app.beacon.core.net.types.Kv
import app.beacon.core.request.C
import app.beacon.modules.*
import app.beacon.modules.open.*
import app.beacon.state.SecreteStore

@OptIn(ExperimentalUnsignedTypes::class)
class Router {
    suspend fun route(args: Args): Frame {
        return if (args.sts == 0u) {
            when (args.kv.get(C.ROUTE)) {
                Auth.name -> Auth
                Info.name -> Info
                Pair.name -> Pair

                else -> NotFound
            }.work(args).asFrame()
        } else {
            if (SecreteStore.have(args.sts)) {
                when (args.kv.get(C.ROUTE)) {
                    Notification.name -> Notification
                    CallReceiver.name -> CallReceiver
                    Ring.name -> Ring
                    Info.name -> Info
                    Auth.name -> Auth
                    Pair.name -> Pair

                    else -> NotFound
                }.work(args).asFrame()
            } else {
                Frame.from(
                    Kv().apply {
                        put(C.ERROR , "secrete")
                        put(C.CODE , "4")
                    }
                )
            }
        }
    }
}