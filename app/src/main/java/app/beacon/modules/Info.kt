package app.beacon.modules

import android.os.Build
import app.beacon.core.request.C
import app.beacon.core.routes.Args
import app.beacon.core.routes.Module
import app.beacon.state.Globals
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
object Info: Module {
    override val name: String = C.INFO

    override suspend fun work(args: Args): Module.Result {

        return Module.Result.ok().with {
            put(C.UUID , Globals.inf.uuid.toString() )
            put(C.BUILD_MANUFACTURER , Globals.inf.vendor)
            put(C.BUILD_MODEL , Build.MODEL)
            put(C.BUILD_VERSION , Globals.inf.osVersion)
        }
    }
}