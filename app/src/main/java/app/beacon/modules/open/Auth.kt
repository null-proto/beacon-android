package app.beacon.modules.open

import app.beacon.core.request.C
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module
import app.beacon.state.SecreteStore
import app.beacon.state.Session

object Auth : Module {
    override val name: String = C.M_AUTH
    override suspend fun work(args: Args): Module.Result {

        val uuid = args.kv.get(C.UUID)
        val lts = args.kv.get(C.LONG_TERM_SECRETE)

        return if (uuid != null && lts != null && args.state.registry.checkLongTermSecrete(uuid = uuid , lts =lts)) {
            SecreteStore.add(uuid = uuid , ip = args.ip)
            Module.Result.ok()
        } else {
            Module.Result.error(code = 4).with {
                put(C.MESSAGE , "long term secrete not found")
            }
        }
    }
}