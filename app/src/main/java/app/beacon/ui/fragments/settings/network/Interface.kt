package app.beacon.ui.fragments.settings.network

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.beacon.ui.components.block.TextBlock
import app.beacon.ui.components.option.ItemSwitchExt
import java.net.NetworkInterface

@Composable fun Interface() {
    val interfaces = remember { NetworkInterface.getNetworkInterfaces().toList() };


    Column {
        TextBlock("Select available interfaces to use")


        Column(
            modifier = Modifier.fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            interfaces.forEach {
                val address = it.interfaceAddresses.joinToString("\n");
                ItemSwitchExt(
                    name = it.displayName,
                    description = "iface: ${it.name}\nup:${it.isUp}\nmcast:${it.supportsMulticast()}\nhwaddr:${it.hardwareAddress}\nifaddr:\n$address",
                    onClick = {
                    }
                )
            }
        }
    }
}