package app.beacon.core.state

import android.content.Context
import app.beacon.core.helpers.Serde
import app.beacon.core.net.Link
import app.beacon.core.net.packet.Header
import app.beacon.core.net.packet.Metadata
import app.beacon.core.net.packet.Packet
import app.beacon.core.net.packet.Payload
import app.beacon.core.net.tcp.TcpListener
import java.nio.file.Files.size

/// Active objects
class Session(context: Context) {
    val tcpListener = TcpListener()

    init {
        tcpListener.start(this)
    }

    fun stop() {
        tcpListener.stop()
    }

    private fun readPacket(link: Link) : Packet {
        val header = Header(link.receive(22))
        val payloadSize = header.payloadLength
        val metadataSize = header.metadataLength
        return Packet(
            header = header,
            metadata = when (metadataSize) {
                0 -> null
                else -> Metadata(link.receive(metadataSize))
            },
            payload = when (payloadSize) {
                0 -> null
                else -> Payload(link.receive(payloadSize))
            }
        )
    }

    fun requestHandler(link: Link) {

    }
}