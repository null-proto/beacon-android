package app.beacon.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatelessDB : ViewModel() {
    private val _mcast_db = MutableLiveData(mutableMapOf<String , String>())

    val inner = _mcast_db

    fun push(ip : String , name : String) {
        _mcast_db.value?.put(ip , name)
        _mcast_db.value = _mcast_db.value
    }

    operator fun get(key: String) : String? {
        return _mcast_db.value[key]
    }

    fun clear() {
        _mcast_db.value = mutableMapOf<String, String>()
    }

}