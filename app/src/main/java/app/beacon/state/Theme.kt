package app.beacon.state

import androidx.compose.runtime.mutableStateOf

object Theme {
    var dynamicTheme =  mutableStateOf(false)
    var mode =  mutableStateOf("system")
}