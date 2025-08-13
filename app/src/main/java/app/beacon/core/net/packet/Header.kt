package app.beacon.core.net.packet

import app.beacon.core.helpers.Serde
import kotlin.collections.toByteArray
import kotlin.experimental.and

class Header(private var data : ByteArray) : Serde {
    companion object Static {
        const val VERSION: Byte = 0
        @JvmStatic
        fun from(
            flags: Flags,
            type: Type,
            payloadId: Int = 0,
            metadataLength: Int = 0,
            payloadLength: Int = 0,
            metadataType: Int = 0,
            checksum: ByteArray = byteArrayOf(0, 0, 0, 0)
        ): Header {
            return Header(
                byteArrayOf(VERSION) +
                        flags.serialize() +
                        type.serialize() +
                        // TODO: remove payloadID
                        byteArrayOf((payloadId shr 8).toByte(), (payloadId).toByte()) +
                        byteArrayOf((payloadLength shr 8).toByte(), (payloadLength).toByte()) +
                        byteArrayOf((metadataLength shr 8).toByte(), (metadataLength).toByte()) +
                        // TODO: remove metadataType
                        byteArrayOf(metadataType.toByte()) +
                        (0..7).map { 0.toByte() }.toByteArray() +
                        checksum
            );
        }
    }


    val version: Byte = data[0]
    val flags : Flags = Flags(data[1])
    val type : Type = Type.from(data[2])

    // deprecated
    val payloadId : Int = data[3].toInt() shl 8 or data[4].toInt()

    val payloadLength : Int = data[5].toInt() shl 8 or data[6].toInt()
    val metadataLength : Int = data[7].toInt() shl 8 or data[8].toInt()
    val metadataType:Int = data[9].toInt()
    // reserved 10-17 : 8 Bytes
    val checksum = data.sliceArray(18..21)


    enum class Type: Serde {
        Echo,
        Data,
        Sync,
        Stream,
        File,
        Undefined;

        override fun serialize(): ByteArray {
            return byteArrayOf(
                when (this) {
                    Echo -> 0
                    Data -> 1
                    Sync -> 2
                    Stream -> 3
                    File -> 4
                    else -> 255
                }.toByte()
            )
        }

        companion object Static {
            fun from(data: Byte) : Type {
                return when (data.toInt()) {
                     0 -> Echo
                     1 -> Data
                     2 -> Sync
                     3 -> Stream
                     4 -> File
                    else -> Undefined
                }
            }
        }
    }


    class Flags(private var data : Byte) : Serde {
        companion object Static {
            const val ENABLE_INTEGRITY_CHECK = 0b0000_0001
            const val ENABLE_PAYLOAD_ID = 0b0000_0010
            const val ENABLE_PAYLOAD_REQUESTED = 0b0000_0100
            const val REQUEST = 0b1000_0000

            fun from(
                enableIntegrityCheck : Boolean = false,
                enablePayloadId : Boolean = false,
                payloadFailure : Boolean = false,
                isRequest : Boolean = false
            ) : Flags {
                val data = ( if (enableIntegrityCheck) { ENABLE_INTEGRITY_CHECK } else { 0 } or
                        if (enablePayloadId) { ENABLE_PAYLOAD_ID } else { 0 } or
                        if (payloadFailure) { ENABLE_PAYLOAD_REQUESTED } else { 0 } or
                        if (isRequest) { REQUEST } else { 0 } ).toByte();
                return Flags(data)
            }
        }
//        0b0000_0001 ; bit 1 - integrity check enabled
//        0b0000_0010 ; bit 2 - payload order enabled ( the real use of payload_id segmant )
//        0b0000_0100 ; bit 3 - payload request - if integrity check failed payload request will send (with same payload_id, payload_length, cheksum)
//        0b1000_0100 ; bit 8 - 1 = request ; 0 = response
//        0b0111_1000 ; other - future use
        val hasIntegrityCheck = (data and 0b0000_0001).toInt() != 0
        val hasPayloadId = (data and 0b0000_0010).toInt() != 0
        val isPayloadFailure = (data and 0b0000_0100).toInt() != 0
        val isRequest = (data.toInt() and 0b1000_0000) != 0

        override fun serialize(): ByteArray {
            return byteArrayOf(data)
        }
    }


    override fun serialize(): ByteArray {
        return data
    }
}
