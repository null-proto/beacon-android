package app.beacon.core

@OptIn(ExperimentalUnsignedTypes::class)
interface Serde<T> {
    fun serialize() : UByteArray
    fun deserialize(data : UByteArray) : T?
}