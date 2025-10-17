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

    object Notification {
        object MainChannelDaemon {
            const val ID = "Daemon"
            const val NAME = "Daemon"
            const val TITLE = "Daemon is running"
            const val DESCRIPTION = ""
        }
    }

    object PreferenceKeys {
        object Theme {
            const val NAME = "theme-settings";

            // String : "system" | "light" | "dark"
            const val APP_THEME = "app-theme-dark-light";

            // Boolean
            const val DYNAMIC_THEME = "dynamic-theme-android12+";
        }
    }
}