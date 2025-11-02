package app.beacon.ui.fragments.settings

import androidx.compose.runtime.Composable
import app.beacon.ui.components.option.Item

@Composable fun Network() {
    Item(
        name = "Network configuration",
        description = "Edit network address,ports,etc..."
    )
}