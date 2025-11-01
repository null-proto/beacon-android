package app.beacon.ui.layouts.settings

import android.content.Context
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import app.beacon.state.Globals
import app.beacon.ui.components.option.ItemSwitch
import app.beacon.ui.components.option.SingleChoiceItem
import androidx.core.content.edit
import app.beacon.state.StatelessDB

@Preview
@Composable
fun Appearance(){
    val context = LocalContext.current
    val pref = context.getSharedPreferences(Globals.PreferenceKeys.Theme.NAME , Context.MODE_PRIVATE)
    val dynamicTheme = StatelessDB.dynamicTheme.value
    val theme = StatelessDB.colorMode.value

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        ItemSwitch(
            name = "Dynamic Theme",
            description = "Auto-match your system wallpaper color",
            toggled = dynamicTheme,
            onToggle = {
                pref.edit(commit = true) {
                    putBoolean(Globals.PreferenceKeys.Theme.DYNAMIC_THEME, it)
                }
                StatelessDB.dynamicTheme.value = it
                it
            }
        )

        SingleChoiceItem(
            name = "System color scheme",
            description = "Switch between light,dark or follow system color scheme",
            index = listOf("light" , "dark" , "system"),
            selected = theme,
            onSelect = {
                pref.edit(commit = true) {
                    putString(Globals.PreferenceKeys.Theme.APP_THEME , it)
                }
                StatelessDB.colorMode.value = it
            }
        )
    }

}