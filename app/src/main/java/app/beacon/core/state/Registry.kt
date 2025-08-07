package app.beacon.core.state

import androidx.compose.runtime.mutableStateMapOf
import app.beacon.core.state.types.Entry
import java.util.UUID

object Registry {
    private val reg = mutableStateMapOf<UUID, Entry>()

    val values
        get() = reg.values

    operator fun invoke(): Collection<Entry> = reg.values

    operator fun plus(entry: Entry): Registry {
        add(entry)
        return this
    }

    fun add(entry:Entry) {
        reg.put(UUID.randomUUID(),entry)
    }

    fun get(uuid: UUID) : Entry? {
        return reg[uuid]
    }

    fun del(uuid: UUID) : Entry? {
        return reg.remove(uuid)
    }
}