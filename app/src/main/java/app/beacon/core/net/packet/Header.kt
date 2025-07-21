package app.beacon.core.net.packet

import android.R.attr.data
import android.content.pm.Checksum
import android.health.connect.datatypes.units.Length
import android.util.Log
import app.beacon.core.helpers.Serde
import java.io.File
import java.net.Authenticator
import java.nio.ByteBuffer
import kotlin.experimental.and
import kotlin.experimental.or

class Header(var data : ByteArray) {
    companion object Static {
        fun from(
            flags: Flags,
            type: Type,
            payloadId: UInt,
            payloadLength: UInt,
            authenticationBytes: ByteArray,
            checksum: ByteArray
        ) : Header? {
            val data = flags.serialize()+
                type.serialize()+
                byteArrayOf( (payloadId shr 8).toByte(),(payloadId).toByte() )+
                byteArrayOf( (payloadLength shr 16).toByte(),(payloadLength shr 8).toByte(), (payloadLength).toByte() )+
                authenticationBytes+
                checksum;

            if (data.size == 16) {
                return Header(data)
            }
            Log.e("Header:from", "header length not satisfied [ required 16 found ${data.size} ]")
            return null
        }
    }
    var version: Byte = data[0]
    var flags : Byte = data[1]
    var type = data[2]
    var payloadId : UInt = (
            (data[3].toUInt() and 0xFFu) shl 8 or
            (data[4].toUInt() and 0xFFu)
    )
        set(value) {
            data[3] = (value shr 8).toByte()
            data[3] = value.toByte()
            field = value
        }
    var payloadLength : UInt = (
            (data[5].toUInt() and 0xFFu) shl 16 or
            (data[6].toUInt() and 0xFFu) shl 8 or
            (data[7].toUInt() and 0xFFu)
    )
        set(value) {
            data[5] = (value shr 16).toByte()
            data[6] = (value shr 8).toByte()
            data[7] = value.toByte()
            field = value
        }
    var authenticationBytes : ByteBuffer = ByteBuffer.wrap(data , 8 , 4)
//            (data[8].toUInt() and 0xFFu) shl 24 or
//            (data[9].toUInt() and 0xFFu) shl 16 or
//            (data[10].toUInt() and 0xFFu) shl 8 or
//            (data[11].toUInt() and 0xFFu)
//    )
//        set(value) {
//            data[8] = (value shr 24).toByte()
//            data[9] = (value shr 16).toByte()
//            data[10] = (value shr 8).toByte()
//            data[11] = value.toByte()
//            field = value
//        }
    var checksum: ByteBuffer = ByteBuffer.wrap(data , 12 ,4)


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
    }


    class Flags(var data : Byte) : Serde {
        companion object Static {
            const val ENABLE_INTEGRITY_CHECK = 0b0000_0001
            const val ENABLE_PAYLOAD_ID = 0b0000_0010
            const val ENABLE_PAYLOAD_REQUESTED = 0b0000_0100

            fun from(
                integrityCheck : Boolean,
                payloadId : Boolean,
                payloadRequest : Boolean,
            ) : Flags {
                val data = ( if (integrityCheck) { ENABLE_INTEGRITY_CHECK } else { 0 } or
                        if (payloadId) { ENABLE_PAYLOAD_ID } else { 0 } or
                        if (payloadRequest) { ENABLE_PAYLOAD_REQUESTED } else { 0 } ).toByte();
                return Flags(data)
            }
        }
//        0b0000_0001 ; bit 1 - integrity check enabled
//        0b0000_0010 ; bit 2 - payload order enabled ( the real use of payload_id segmant )
//        0b0000_0100 ; bit 3 - payload request - if integrity check failed payload request will send (with same payload_id, payload_length, cheksum)
//        0b1111_1000 ; other - future use
        var integrityCheck = (data and 0b0000_0001).toInt() != 0
            set(value) {
                data = if (value) {
                    data or ENABLE_INTEGRITY_CHECK.toByte()
                } else {
                    data and ENABLE_INTEGRITY_CHECK.toByte()
                }
                field = value
            }
        var payloadId = (data and 0b0000_0010).toInt() != 0
            set(value) {
                data = if (value) {
                    data or ENABLE_PAYLOAD_ID.toByte()
                } else {
                    data and ENABLE_PAYLOAD_ID.toByte()
                }
                field = value
            }
        var payloadRequest = (data and 0b0000_0100).toInt() != 0
            set(value) {
                data = if (value) {
                    data or ENABLE_PAYLOAD_REQUESTED.toByte()
                } else {
                    data and ENABLE_PAYLOAD_REQUESTED.toByte()
                }
                field = value
            }

        override fun serialize(): ByteArray {
            return byteArrayOf(data)
        }
    }
}