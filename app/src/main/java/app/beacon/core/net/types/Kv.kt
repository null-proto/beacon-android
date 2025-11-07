package app.beacon.core.net.types

import android.util.Log
import androidx.compose.ui.platform.LocalGraphicsContext
import app.beacon.core.Serde

@OptIn(ExperimentalUnsignedTypes::class)
class Kv( private var data : UByteArray  = ubyteArrayOf() ) : Serde<Kv> {
    val len : Int
        get() {
            return data.size
        }

    val size : Int
        get() {
            return keys().size
        }


    fun put(key : String, value : String ) {
        if (key.isEmpty() || value.isEmpty()) {
            return
        }
        val keySerialized = key.encodeToByteArray().toUByteArray()
        val valueSerialized = value.encodeToByteArray().toUByteArray()
        data += ubyteArrayOf(keySerialized.size.toUByte() , (keySerialized.size shr 8).toUByte()) + keySerialized
        data += ubyteArrayOf(valueSerialized.size.toUByte(),(valueSerialized.size shr 8).toUByte() ) + valueSerialized
    }

    fun get(key: String) : String? {
        var cur = 0
        var pre = 0
        val keyByte = key.encodeToByteArray().toUByteArray()

        while (data.size > cur) {
            cur += ((data[cur].toInt() or (data[cur+1].toInt() shl 8) )) +2
            if (cur-pre-2 == keyByte.size) {
                val target = data.sliceArray( (pre+2)..<cur )
                pre = cur
                cur += (data[pre].toInt() or (data[pre+1].toInt() shl 8))+2
                if (target.contentEquals(keyByte)) {
                    return String( data.sliceArray( (pre+2)..<cur ).asByteArray() )
                }
            } else {
                cur += (data[cur].toInt() or (data[cur+1].toInt() shl 8) )+2
            }
            pre = cur
        }
        return null
    }

    fun keys() :List<String> {
        var cur = 0
        var pre = 0
        val list = mutableListOf<String>()

        while(data.size > cur) {
            cur += (data[cur].toInt() or (data[cur+1].toInt() shl 8) )+2
            list += String(data.sliceArray(pre+2..<cur).asByteArray())
            cur += (data[cur].toInt() or (data[cur+1].toInt() shl 8) )+2
            pre = cur
        }

        return list.toList()
    }

    override fun serialize(): UByteArray {
        return data
    }

    override fun deserialize(data: UByteArray): Kv {
        return Kv(data)
    }
}
