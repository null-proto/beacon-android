package app.beacon.core.state.types

class RequestRouter {
    val map = mutableMapOf<String,(DeviceEntry, List<Any>)->Any>(
        Pair("/") { _,_ ->  },
    )

    private fun default(deviceEntry: DeviceEntry, arg : Any) {

    }

    fun add(path: String , proc : (DeviceEntry, List<Any>)-> Any) {
        map.put(path,proc)
    }

    fun call(path: String, deviceEntry: DeviceEntry, arg: List<Any>) : Any {
        map[path]?.let {
            return it(deviceEntry,arg)
        }
        return default(deviceEntry,arg)
    }
}