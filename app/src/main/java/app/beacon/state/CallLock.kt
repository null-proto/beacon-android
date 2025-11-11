package app.beacon.state

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

object CallLock: ViewModel() {
    var lock = false
        private set

    var ongoingCall = mutableStateOf(false)
    var initiate = false

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
        initiate = false
        ongoingCall.value = false
        lock = false
    }
}