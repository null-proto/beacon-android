package app.beacon.state

import java.net.InetAddress
import kotlin.random.Random
import kotlin.random.nextUInt
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
object SecreteStore {
    private val store = hashMapOf<UInt , String>()
    private val _ip = hashMapOf<UInt , InetAddress>()
    private val rand = Random((0..99999).random())

    operator fun get(sts: UInt) : String? {
        return store[sts]
    }

    operator fun set(sts: UInt , value : String) {
        store.put(sts , value)
    }

    fun getIp(sts : UInt) : InetAddress? {
        return _ip.get(sts)
    }

    fun setIp(sts : UInt, ip : InetAddress) {
        _ip.put(sts , ip)
    }

    fun add(uuid : String , ip : InetAddress) : UInt {
        val sts = rand.nextUInt()
        store.put(sts , uuid)
        _ip.put(sts , ip)
        return sts
    }

    fun rm(sts : UInt) {
        store.remove(sts)
        _ip.remove(sts)
    }

    fun have(sts: UInt) : Boolean {
        return store.contains(sts)
    }

    fun flush() {
        store.clear()
        _ip.clear()
    }
}