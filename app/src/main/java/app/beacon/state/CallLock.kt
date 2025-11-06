package app.beacon.state

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

object CallLock: ViewModel() {
    private var lock = false
    var ongoingCall = mutableStateOf(false)
    var initiated = mutableStateOf(false)


    val isLocked : Boolean
        get() { return lock }

    var initiate : Boolean
        get() { return initiated.value }
        set(value) { initiated.value = value }

    fun lock(): Boolean {
        if (!lock) {
            lock = true
            ongoingCall.value = true
            return true
        } else {
            return false
        }
    }


    fun unlock() {
        initiated.value = false
        ongoingCall.value = false
        lock = false
    }
}