package app.beacon.core.request

object C {
    // Routeing
    const val ROUTE = "route"
    const val CALL_RECEIVER = "call.rx"
    const val RING = "mk.ring"
    const val NOTIFY = "mk.notify"
    const val NOT_FOUND = "not.fnd"


    // connection mata data
    const val ID = "id"
    const val CODE = "returns"
    const val RESULT = "result"
    const val ERROR = "error"
    const val OK = "ok"
    const val MESSAGE = "msg"

    // information exchange
    const val UUID = "uuid"
    const val SECRETE = "short.sec"
    const val LONG_TERM_SECRETE = "long.sec"
    const val INET_ADDRESS_4 = "ipv4-a"
    const val INET_ADDRESS_6 = "ipv6-a"
    const val CURRENT_INTERFACE = ".iface"
    const val BUILD_MANUFACTURER = "vendor"
    const val BUILD_MODEL = "model"
    const val BUILD_VERSION = "os.ver"
}