package app.beacon.core.net.packet

import app.beacon.core.helpers.Serde

class MetaPayload() : Serde {
    val routeLen : UInt = 0u
    val route : String = ""

    enum class Type: Serde {
        Unknown,
        Route,
        Json;

        companion object Static {
            @JvmStatic fun from(type: Int) : Type {
                return when (type) {
                    1 -> Json
                    2 -> Route
                    else -> Unknown
                }
            }
        }

        override fun serialize(): ByteArray {
            return byteArrayOf(
                when (this) {
                    Json -> 1
                    Route -> 2
                    else -> 0
            })
        }
    }

    override fun serialize(): ByteArray {
        TODO("Not yet implemented")
    }
}