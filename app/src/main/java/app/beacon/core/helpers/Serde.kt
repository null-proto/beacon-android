package app.beacon.core.helpers

interface Serde {
    fun serialize() : ByteArray
    // fun <T> deserialize(data: ByteArray) : T?
}