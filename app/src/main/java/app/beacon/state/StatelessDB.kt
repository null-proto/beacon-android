package app.beacon.state

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class StatelessDB : ViewModel() {
    private val _state = mutableStateMapOf<String , String>()

    val state = _state

    fun push(ip : String , name : String) {
        _state.put(ip , name)
    }

    operator fun get(key: String) : String? {
        return _state[key]
    }
    operator fun set(key: String , value: String) {
        _state.put(key,value)
    }

    fun clear() {
        _state.clear()
    }

}