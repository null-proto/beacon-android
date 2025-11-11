package app.beacon.modules.open

import app.beacon.core.database.schema.DevInfo
import app.beacon.core.database.schema.SecreteStore
import app.beacon.core.request.C
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module
import app.beacon.state.PairNg

object Pair : Module {
    override val name: String = C.M_PAIR

    override suspend fun work(args: Args): Module.Result {
        val uuid = args.kv.get(C.UUID)
        val lts = args.kv.get(C.LONG_TERM_SECRETE)
        val name = args.kv.get(C.BUILD_MODEL)

        val mode = args.kv.get(C.M_PAIR_MODE)
        val id = args.kv.get(C.ID)

        if (uuid != null && lts != null && name != null && mode !=null && id != null) {

            args.state.registry.insert(DevInfo(
                uuid = uuid,
                name = name,
                lastIp = args.ip.hostName
            ))
            PairNg.code.put(id , args.ip)


//            args.state.registry.insert(SecreteStore(
//                uuid = uuid,
//                longTermSecrete = lts
//            ))
            return Module.Result.ok()
        } else {
            return Module.Result.error(code = 1)
        }
    }
}