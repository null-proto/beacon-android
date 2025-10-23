package app.beacon.core.device

import app.beacon.core.Serde
import app.beacon.core.net.types.Kv

@OptIn(ExperimentalUnsignedTypes::class)
data class Device(
    val ipAddress: String,
    val name : String?,
) : Serde<Device> {

    override fun serialize(): UByteArray {
        val pb = Kv().apply {
            put("ip",ipAddress)
            put("name" , name ?: ipAddress )
        }
        return pb.serialize()
    }

    override fun deserialize(data: UByteArray): Device? {
        val pb = Kv(data);
        val ipAddress = pb.get("ip") ?: return null;
        return Device(
            ipAddress = ipAddress,
            name = pb.get("name") ?: ipAddress
        )
    }
}