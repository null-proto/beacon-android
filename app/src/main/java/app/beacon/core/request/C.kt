package app.beacon.core.request

object C {
    // Routeing
    const val ROUTE = "route"
    const val CALL_RECEIVER = "call.rx"
    const val RING = "mk.ring"
    const val NOTIFY = "mk.notify"
    const val INFO = "info"
    const val NOT_FOUND = "not.fnd"

    const val M_AUTH = ".auth"
    const val M_PAIR = ".pair"

    // module options
    const val M_PAIR_MODE = ".pair:"
    const val M_PAIR_MODE_1 = ".pair:1"
    const val M_PAIR_MODE_2 = ".pair:2"
    const val M_PAIR_MODE_3 = ".pair:3"


    // connection mata data
    const val ID = "id"
    const val CODE = "returns"
    const val RESULT = "result"
    const val ERROR = "error"
    const val OK = "ok"
    const val MESSAGE = "msg"

    // information exchange
    const val UUID = "uuid"
    const val SECRETE = "sec.s"
    const val LONG_TERM_SECRETE = "sec.l"
    const val INET_ADDRESS_4 = "ipv4-a"
    const val INET_ADDRESS_6 = "ipv6-a"
    const val CURRENT_INTERFACE = ".iface"
    const val BUILD_MANUFACTURER = "vendor"
    const val BUILD_MODEL = "model"
    const val BUILD_VERSION = "os.ver"
}