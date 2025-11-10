package app.beacon.modules.open

import app.beacon.core.routes.Args
import app.beacon.core.routes.Module

class Pair : Module {
    override val name: String = ""

    override suspend fun work(args: Args): Module.Result {

        return Module.Result.ok()
    }
}