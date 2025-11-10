package app.beacon.state

import kotlin.random.Random
import kotlin.random.nextUInt
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
object SecreteStore {
    private val store = hashMapOf<UInt , Uuid>()
    private val rand = Random((0..99999).random())

    operator fun get(sts: UInt) : Uuid? {
        return store[sts]
    }

    operator fun set(sts: UInt , value : Uuid) {
        store.put(sts , value)
    }

    fun add(uuid : Uuid) : UInt {
        val sts = rand.nextUInt()
        store.put(sts , uuid)
        return sts
    }

    fun rm(sts : UInt) {
        store.remove(sts)
    }

    fun flush() {
        store.clear()
    }
}