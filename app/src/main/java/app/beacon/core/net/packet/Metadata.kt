package app.beacon.core.net.packet

import androidx.compose.material3.MediumTopAppBar
import app.beacon.core.helpers.Serde
import app.beacon.core.state.types.KvStore
import org.json.JSONObject

class Metadata(val data : JSONObject) : Serde {
    override fun serialize(): ByteArray {
        return this.data.toString().toByteArray()
    }

    operator fun get(key: String) : String? {
        return data.getString(key)
    }

    operator fun set(key : String , value : String) {
        data.put(key,value)
    }

    companion object {
        const val HEADER_SIZE = 22

        fun from(map: JSONObject) : Metadata {
            return Metadata(map)
        }

        fun from(map : Serde): Metadata {
            return Metadata(JSONObject(map.serialize().toString()))
        }
    }
}