package app.beacon.core.device

import app.beacon.core.PairBox
import app.beacon.core.Serde

@OptIn(ExperimentalUnsignedTypes::class)
data class Device(
    val ipAddress: String,
    val name : String?,
) : Serde<Device> {

    override fun serialize(): UByteArray {
        val pb = PairBox().apply {
            put("ip",ipAddress)
            put("name" , name ?: ipAddress )
        }
        return pb.serialize()
    }

    override fun deserialize(data: UByteArray): Device? {
        val pb = PairBox(data);
        val ipAddress = pb.get("ip") ?: return null;
        return Device(
            ipAddress = ipAddress,
            name = pb.get("name") ?: ipAddress
        )
    }
}