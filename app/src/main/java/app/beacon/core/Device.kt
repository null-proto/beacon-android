package app.beacon.core

import app.beacon.R.drawable
import app.beacon.core.helpers.Serde
import org.json.JSONObject

class Device {
    interface IntoDeviceType : Serde {
        fun getIcon() : Int
    }

    interface IntoDeviceInfo : Serde {
        val name : String
        val type : DeviceType
        val hostname : String
    }

    enum class DeviceType: IntoDeviceType {
        Laptop,
        Desktop,
        Mobile,
        Tv,
        Tablet,
        Unknown;

        override fun getIcon() : Int {
            return when(this) {
                Laptop -> {
                    drawable.laptop_minimal
                }
                Desktop -> {
                    drawable.monitor
                }
                Mobile -> {
                    drawable.smartphone
                }
                Tv -> {
                    drawable.tv
                }
                Tablet -> {
                    drawable.tablet
                }
                Unknown -> {
                    drawable.circle_question
                }
            }
        }

        override fun serialize(): ByteArray {
            return byteArrayOf(when(this) {
                Laptop ->  0x1
                Desktop -> 0x2
                Mobile ->  0x3
                Tv ->      0x4
                Tablet ->  0x5
                Unknown -> 0x6
            })
        }

        companion object {
            @JvmStatic
            fun deserialize(data: ByteArray): DeviceType {
                return when (data[0]) {
                    0x1.toByte() -> Laptop
                    0x2.toByte() -> Desktop
                    0x3.toByte() -> Mobile
                    0x4.toByte() -> Tv
                    0x5.toByte() -> Tablet
                    else -> Unknown
                }
            }
        }
    }

    data class DevicePublicInfo(
        override val name: String,
        override val type : DeviceType,
        override val hostname : String
    ) : IntoDeviceInfo {
        override fun serialize(): ByteArray {
            return JSONObject().apply {
                put("name", name)
                put("type", type)
                put("hostname", hostname)
            }.toString().toByteArray(Charsets.UTF_8)
        }


        companion object {
            @JvmStatic
            fun deserialize(data: ByteArray): DevicePublicInfo {
                JSONObject(data.slice(1..-1).toByteArray().toString(Charsets.UTF_8)).apply {
                    return DevicePublicInfo(
                        getString("name"),
                        DeviceType.deserialize(byteArrayOf(data[0])),
                        getString("hostname")
                    )
                }
            }
        }
    }

    data class DeviceFullInfo(
        override val name: String,
        override val type : DeviceType,
        override val hostname : String
    ) : IntoDeviceInfo {

        override fun serialize(): ByteArray {
            return JSONObject().apply {
                put("name", name)
                put("type", type)
                put("hostname", hostname)
            }.toString().toByteArray(Charsets.UTF_8)
        }

        companion object {
            @JvmStatic
            fun deserialize(data: ByteArray): DeviceFullInfo {
                JSONObject(data.slice(1..-1).toByteArray().toString(Charsets.UTF_8)).apply {
                    return DeviceFullInfo(
                        getString("name"),
                        DeviceType.deserialize(byteArrayOf(data[0])),
                        getString("hostname")
                    )
                }
            }
        }
    }
}