package app.beacon.core.net.packet

import android.util.Log
import app.beacon.core.helpers.Serde
import kotlin.experimental.and

//class Header(var data : ByteArray) {
//    companion object Static {
//        fun from(
//            flags: Flags,
//            type: Type,
//            payloadId: UInt,
//            payloadLength: UInt,
//            authenticationBytes: ByteArray,
//            checksum: ByteArray
//        ) : Header? {
//            val data = flags.serialize()+
//                type.serialize()+
//                byteArrayOf( (payloadId shr 8).toByte(),(payloadId).toByte() )+
//                byteArrayOf( (payloadLength shr 16).toByte(),(payloadLength shr 8).toByte(), (payloadLength).toByte() )+
//                authenticationBytes+
//                checksum;
//
//            if (data.size == 16) {
//                return Header(data)
//            }
//            Log.e("Header:from", "header length not satisfied [ required 16 found ${data.size} ]")
//            return null
//        }
//    }
//    var version: Byte = data[0]
//    var flags : Byte = data[1]
//    var type = data[2]
//    var payloadId : UInt = (
//            (data[3].toUInt() and 0xFFu) shl 8 or
//            (data[4].toUInt() and 0xFFu)
//    )
//        set(value) {
//            data[3] = (value shr 8).toByte()
//            data[3] = value.toByte()
//            field = value
//        }
//    var payloadLength : UInt = (
//            (data[5].toUInt() and 0xFFu) shl 16 or
//            (data[6].toUInt() and 0xFFu) shl 8 or
//            (data[7].toUInt() and 0xFFu)
//    )
//        set(value) {
//            data[5] = (value shr 16).toByte()
//            data[6] = (value shr 8).toByte()
//            data[7] = value.toByte()
//            field = value
//        }
//    var authenticationBytes : ByteBuffer = ByteBuffer.wrap(data , 8 , 4)
////            (data[8].toUInt() and 0xFFu) shl 24 or
////            (data[9].toUInt() and 0xFFu) shl 16 or
////            (data[10].toUInt() and 0xFFu) shl 8 or
////            (data[11].toUInt() and 0xFFu)
////    )
////        set(value) {
////            data[8] = (value shr 24).toByte()
////            data[9] = (value shr 16).toByte()
////            data[10] = (value shr 8).toByte()
////            data[11] = value.toByte()
////            field = value
////        }
//    var checksum: ByteBuffer = ByteBuffer.wrap(data , 12 ,4)
//
//
//    enum class Type: Serde {
//        Echo,
//        Data,
//        Sync,
//        Stream,
//        File,
//
//        Undefined;
//
//        override fun serialize(): ByteArray {
//            return byteArrayOf(
//                when (this) {
//                    Echo -> 0
//                    Data -> 1
//                    Sync -> 2
//                    Stream -> 3
//                    File -> 4
//                    else -> 255
//                }.toByte()
//            )
//        }
//    }
//
//
//    class Flags(var data : Byte) : Serde {
//        companion object Static {
//            const val ENABLE_INTEGRITY_CHECK = 0b0000_0001
//            const val ENABLE_PAYLOAD_ID = 0b0000_0010
//            const val ENABLE_PAYLOAD_REQUESTED = 0b0000_0100
//
//            fun from(
//                integrityCheck : Boolean,
//                payloadId : Boolean,
//                payloadRequest : Boolean,
//            ) : Flags {
//                val data = ( if (integrityCheck) { ENABLE_INTEGRITY_CHECK } else { 0 } or
//                        if (payloadId) { ENABLE_PAYLOAD_ID } else { 0 } or
//                        if (payloadRequest) { ENABLE_PAYLOAD_REQUESTED } else { 0 } ).toByte();
//                return Flags(data)
//            }
//        }
////        0b0000_0001 ; bit 1 - integrity check enabled
////        0b0000_0010 ; bit 2 - payload order enabled ( the real use of payload_id segmant )
////        0b0000_0100 ; bit 3 - payload request - if integrity check failed payload request will send (with same payload_id, payload_length, cheksum)
////        0b1111_1000 ; other - future use
//        var integrityCheck = (data and 0b0000_0001).toInt() != 0
//            set(value) {
//                data = if (value) {
//                    data or ENABLE_INTEGRITY_CHECK.toByte()
//                } else {
//                    data and ENABLE_INTEGRITY_CHECK.toByte()
//                }
//                field = value
//            }
//        var payloadId = (data and 0b0000_0010).toInt() != 0
//            set(value) {
//                data = if (value) {
//                    data or ENABLE_PAYLOAD_ID.toByte()
//                } else {
//                    data and ENABLE_PAYLOAD_ID.toByte()
//                }
//                field = value
//            }
//        var payloadRequest = (data and 0b0000_0100).toInt() != 0
//            set(value) {
//                data = if (value) {
//                    data or ENABLE_PAYLOAD_REQUESTED.toByte()
//                } else {
//                    data and ENABLE_PAYLOAD_REQUESTED.toByte()
//                }
//                field = value
//            }
//
//        override fun serialize(): ByteArray {
//            return byteArrayOf(data)
//        }
//    }
//}



class Header(var data : ByteArray) : Serde {
    companion object Static {
        @JvmStatic fun from(
            flags: Flags,
            type: Type,
            payloadId: Int = 0,
            payloadLength: Int = 0,
            authenticationBytes: ByteArray = byteArrayOf(0,0,0,0),
            checksum: ByteArray = byteArrayOf(0,0,0,0)
        ) : Header? {
            val data = flags.serialize()+
                    type.serialize()+
                    byteArrayOf( (payloadId shr 8).toByte(),(payloadId).toByte() )+
                    byteArrayOf( (payloadLength shr 16).toByte(),(payloadLength shr 8).toByte(), (payloadLength).toByte() )+
                    authenticationBytes+
                    checksum;

            if ( data.size == 16) {
                return Header(data)
            }
            Log.e("Header:from", "header length not satisfied [ required 16 found ${data.size} ]")
            return null
        }

        @JvmStatic fun serialize(
            flags: Header.Flags,
            type: Header.Type,
            payloadId: Int = 0,
            payloadLength: Int = 0,
            authenticationBytes: ByteArray = byteArrayOf(0,0,0,0),
            checksum: ByteArray = byteArrayOf(0,0,0,0)
        ) : ByteArray {
            return flags.serialize() +
                    type.serialize() +
                    byteArrayOf((payloadId shr 8).toByte(), (payloadId).toByte()) +
                    byteArrayOf(
                        (payloadLength shr 16).toByte(),
                        (payloadLength shr 8).toByte(),
                        (payloadLength).toByte()
                    ) +
                    authenticationBytes +
                    checksum;
        }
    }
    val version: Byte = data[0]
    val flags : Flags = Flags(data[1])
    val type : Type = Type.from(data[2])
    val payloadId : Int = (
            (data[3].toInt() and 0xFF) shl 8 or
                    (data[4].toInt() and 0xFF)
            )
    val payloadLength : Int = (
            (data[5].toInt() and 0xFF) shl 16 or
                    (data[6].toInt() and 0xFF) shl 8 or
                    (data[7].toInt() and 0xFF)
            )
    val authenticationBytes = data.sliceArray(8..11)
    val checksum = data.sliceArray(12..15)


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


    class Flags(var data : Byte) : Serde {
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
