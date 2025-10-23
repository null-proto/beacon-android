package app.beacon.core.net.types

@OptIn(ExperimentalUnsignedTypes::class)
data class Bin(
    val data : ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Bin

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }

    companion object Static {
        fun fromUByteArray(date: UByteArray) : Bin {
            return Bin(
                data = date.map { it.toByte() }.toByteArray(),
            )
        }
    }

    fun toByteArray() : UByteArray {
        return data.map { it.toUByte() }.toUByteArray()
    }

    val size
        get() = data.size

    
}