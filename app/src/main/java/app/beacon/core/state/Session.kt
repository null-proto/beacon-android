package app.beacon.core.state

import app.beacon.core.net.Link
import app.beacon.core.net.packet.Header
import app.beacon.core.net.packet.Packet
import app.beacon.core.net.packet.Payload
import app.beacon.core.net.tcp.TcpListener

/// Active objects
class Session {
    val tcpListener = TcpListener()

    init {
        tcpListener.start(this)
    }

    private fun readHeader(link : Link) : Header? {
        return Header(link.receive(16))
    }

    private fun readPacket(link: Link) : Packet? {
        val header = readHeader(link)
        val size = header?.payloadLength
        return when (size) {
            null -> null

            0 -> Packet(header)

            else -> {
                val body = link.receive(size)
                val payload = Payload(body)
                Packet(
                    header,
                    payload = payload
                )
            }
        }
    }

    fun responseHandler() {

    }

    fun echo(link: Link, request: Boolean) {
        link.send(
            Header.from(
                flags = Header.Flags.from(
                    isRequest = request
                ),
                type = Header.Type.Echo,
            )
        )
    }

    fun requestHandler(link: Link) {
    }
}