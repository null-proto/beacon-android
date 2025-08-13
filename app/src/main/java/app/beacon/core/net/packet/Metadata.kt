package app.beacon.core.net.packet

import androidx.compose.material3.MediumTopAppBar
import app.beacon.core.helpers.Serde
import app.beacon.core.state.types.KvStore
import org.json.JSONObject

class Metadata(var data : ByteArray) : Serde {
    override fun serialize(): ByteArray {
        return this.data
    }

    operator fun get(key: String) : String? {
        var previous: Int = 0
        var current: Int = 0
        val keySerialized = key.toByteArray()
        while (data.size > current) {
            previous = current
            current = data[current].toInt()+1

            val keySlice = data.sliceArray(previous+1..<current)
            if (keySerialized.size == current-previous-1 && keySerialized.contentEquals(keySlice)) {
                previous = current
                current = data[current].toInt()+1
                return String(data.sliceArray(previous + 1..<current))
            } else {
                current = data[current].toInt()+1
            }
        }
        return null
    }

    fun put(key : String , value : String) {
        if (!key.isEmpty() && !value.isEmpty()) {
            val keySerialized = key.toByteArray()
            val valueSerialized = value.toByteArray()
            data += byteArrayOf(keySerialized.size.toByte()) + keySerialized + byteArrayOf(keySerialized.size.toByte()) + valueSerialized
        }
    }
}