package app.beacon.modules.open

import app.beacon.core.request.C
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module

object Auth : Module {
    override val name: String = ""
    override suspend fun work(args: Args): Module.Result {

        val uuid = args.kv?.get(C.UUID)
        val lts = args.kv?.get(C.LONG_TERM_SECRETE)

        return Module.Result.ok()
    }
}