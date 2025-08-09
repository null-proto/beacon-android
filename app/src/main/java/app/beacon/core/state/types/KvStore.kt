package app.beacon.core.state.types

import android.util.Log.i
import app.beacon.core.helpers.Serde

class KvStore(val data : ByteArray) : Serde{

    operator fun get(key: String): String? {
        TODO()
    }

    operator fun set(key: String ,data: String){
    }

    override fun serialize(): ByteArray {
//        return map.entries.joinToString("\r") { "${it.key}:${it.value}" }.toByteArray()
        return data
    }

    companion object Static {
        fun from(data: ByteArray) : KvStore {
            return KvStore(data)
        }
    }
}