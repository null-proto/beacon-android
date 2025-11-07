package app.beacon.state

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

object CallLock: ViewModel() {
    private var lock = false
    var ongoingCall = mutableStateOf(false)
    var initiated = false


    val isLocked : Boolean
        get() { return lock }

    var initiate : Boolean
        get() { return initiated }
        set(value) { initiated = value }

    fun lock(): Boolean {
        Log.d("CallLock" , "locking")
        if (!lock) {
            lock = true
            ongoingCall.value = true
            return true
        } else {
            Log.w("CallLock" , "already locked")
            return false
        }
    }


    fun unlock() {
        Log.d("CallLock" , "Unlocking")
        initiated = false
        ongoingCall.value = false
        lock = false
    }
}