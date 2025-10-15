@file:OptIn(ExperimentalUuidApi::class)

package app.beacon.state

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data object Globals {
    var isDaemonRunning: Boolean = false
    var isScanningMode: Boolean = false
    var inf : Inf = generateNewInf()

    data class Inf (
        val uuid: Uuid
    )

    fun generateNewInf() : Inf {
        return Inf(
            uuid = Uuid.random()
        )
    }
}