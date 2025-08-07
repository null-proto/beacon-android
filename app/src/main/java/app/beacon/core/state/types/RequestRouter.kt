package app.beacon.core.state.types

import app.beacon.core.state.types.Entry

class RequestRouter {
    val map = mutableMapOf<String,(Entry, List<Any>)->Any>(
        Pair("/") { _,_ ->  },
    )

    private fun default(entry: Entry, arg : Any) {

    }

    fun add(path: String , proc : (Entry, List<Any>)-> Any) {
        map.put(path,proc)
    }

    fun call(path: String, entry: Entry, arg: List<Any>) : Any {
        map[path]?.let {
            return it(entry,arg)
        }
        return default(entry,arg)
    }
}