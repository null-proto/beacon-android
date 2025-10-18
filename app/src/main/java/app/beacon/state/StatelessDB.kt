package app.beacon.state

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

object StatelessDB : ViewModel() {
    private val _mcast_db = MutableLiveData(mutableMapOf<String , String>())

    val db = _mcast_db

    fun push(ip : String , name : String) {
        _mcast_db.value?.put(ip , name)
        _mcast_db.value = _mcast_db.value
    }

    fun clear() {
        _mcast_db.value = mutableMapOf<String, String>()
    }

}