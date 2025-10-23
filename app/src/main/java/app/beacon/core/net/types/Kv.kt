package app.beacon.core.net.types

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
        data += ubyteArrayOf(keySerialized.size.toUByte()) + keySerialized
        data += ubyteArrayOf(valueSerialized.size.toUByte()) + valueSerialized
    }

    fun get(key: String) : String? {
        var cur = 0
        var pre = 0
        val keyByte = key.encodeToByteArray().toUByteArray()

        while (data.size > cur) {
            cur += data[cur].toInt()+1 // [0]=3+1=4,
            if (cur-pre-1 == keyByte.size) {
                val target = data.sliceArray(
                    (pre+1)..<cur
                )

                pre = cur
                cur += data[pre].toInt()+1

                if (target.contentEquals(keyByte)) {
                    return String(
                        data.sliceArray(
                            (pre+1)..<cur
                        ).asByteArray()
                    )
                }

            } else {
                cur += data[cur].toInt()+1 // [4]=4+1+(4)=9,
            }
            pre = cur // 9+1=10,
        }
        return null
    }

    fun keys() :List<String> {
        var cur = 0
        var pre = 0
        val list = mutableListOf<String>()


        while(data.size > cur) {
            cur += data[cur].toInt()+1
            list += String(data.sliceArray(pre+1..<cur).asByteArray())
            cur += data[cur].toInt()+1
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
