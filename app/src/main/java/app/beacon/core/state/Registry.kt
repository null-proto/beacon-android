package app.beacon.core.state

import androidx.compose.runtime.mutableStateMapOf
import app.beacon.core.state.types.DeviceEntry
import java.util.UUID

object Registry {
    private val reg = mutableStateMapOf<UUID, DeviceEntry>()

    val values
        get() = reg.values

    operator fun invoke(): Collection<DeviceEntry> = reg.values

    operator fun plus(deviceEntry: DeviceEntry): Registry {
        add(deviceEntry)
        return this
    }

    fun add(deviceEntry:DeviceEntry) {
        reg.put(UUID.randomUUID(),deviceEntry)
    }

    fun get(uuid: UUID) : DeviceEntry? {
        return reg[uuid]
    }

    fun del(uuid: UUID) : DeviceEntry? {
        return reg.remove(uuid)
    }
}