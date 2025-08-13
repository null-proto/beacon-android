package app.beacon.core.net

import app.beacon.core.state.Session
import app.beacon.core.state.types.DeviceEntry

interface LinkProvider {
    fun start(session: Session)
    fun stop()

    fun createLink(deviceEntry: DeviceEntry): Link
}