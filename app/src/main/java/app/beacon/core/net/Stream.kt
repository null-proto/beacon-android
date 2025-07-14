package app.beacon.core.net

interface Stream {
    fun send(data: ByteArray)
    fun receive() : ByteArray
}