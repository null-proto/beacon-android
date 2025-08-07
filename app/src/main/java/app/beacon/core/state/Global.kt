package app.beacon.core.state

object Global {
    var isDaemonRunning = false
    var isMainActivityRunning = false
    var isDiscoveryActivityRunning = false
    var isControlTabActivityRunning = false
    var isSettingsActivityRunning = false

    object Defaults {
        var tcpListenerPortMain : Int = 7501
        var tcpConnectionTimeout : Int = 5000 // 5s
    }

    object Constants {
    }
}