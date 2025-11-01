@file:OptIn(ExperimentalUuidApi::class)

package app.beacon.state

import android.content.Context
import androidx.compose.ui.graphics.vector.VectorConfig
import kotlin.math.cosh
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data object Globals {
    var isDaemonRunning: Boolean = false
    var isCrashed : Boolean = false
    var isIPv6MulticastingRunning: Boolean = false
    var isScanningMode: Boolean = false

    lateinit var inf : Inf

    object RuntimeConfig {
        object Network {
            var port : Int = 4800
            var ip : String = "::"
            var timeout : Int = 5000
        }
    }

    data class Inf (
        val uuid: Uuid
    )

    fun setup(context: Context) {
        this.inf = generateNewInf(context)
        loadNetworkConfig(context)
    }

    fun loadNetworkConfig(context: Context) {
        val pref = context.getSharedPreferences(PreferenceKeys.Network.NAME, Context.MODE_PRIVATE)
        RuntimeConfig.Network.port = pref.getInt(PreferenceKeys.Network.DAEMON_PORT, RuntimeConfig.Network.port)
        RuntimeConfig.Network.timeout = pref.getInt(PreferenceKeys.Network.DAEMON_NET_TIMEOUT, RuntimeConfig.Network.timeout)
        RuntimeConfig.Network.ip = pref.getString(PreferenceKeys.Network.DAEMON_BIND_IP, RuntimeConfig.Network.ip) ?: RuntimeConfig.Network.ip
    }
    fun generateNewInf(context: Context) : Inf {
        val preUuid = context.getSharedPreferences(PreferenceKeys.Self.NAME, Context.MODE_PRIVATE)

        val gUuid = preUuid.getString(PreferenceKeys.Self.UUID, null);

        if (gUuid == null) {
            val gUuid = Uuid.random()
            preUuid.edit().apply {
                putString(PreferenceKeys.Self.UUID, gUuid.toString())
                commit()
            }

            return Inf(gUuid)

        } else {
            return Inf(
                uuid = Uuid.parse(gUuid)
            )
        }

    }

    object Notification {
        object MainChannelDaemon {
            const val CATEGORY = "Service"
            const val ID = "Daemon"
            const val NAME = "Daemon"
            const val TITLE = "Daemon is running"
            const val DESCRIPTION = ""
        }
    }

    object PreferenceKeys {
        object Self {
            const val NAME = "app.beacon"

            const val UUID = "app-uuid"
        }

        object Network {
            const val NAME = "network-pref"

            const val DAEMON_PORT = "daemon-port"

            const val DAEMON_BIND_IP = "daemon-ip"

            const val DAEMON_NET_TIMEOUT = "daemon-net-timeout"
        }

        object Theme {
            const val NAME = "theme-settings";

            // String : "system" | "light" | "dark"
            const val APP_THEME = "app-theme-dark-light";

            // Boolean
            const val DYNAMIC_THEME = "dynamic-theme-android12+";
        }
    }
}