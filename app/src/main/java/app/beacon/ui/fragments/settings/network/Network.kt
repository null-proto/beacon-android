package app.beacon.ui.fragments.settings.network

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import app.beacon.state.Globals.PreferenceKeys
import app.beacon.ui.components.option.ItemContainer
import androidx.core.content.edit
import androidx.navigation.NavController
import app.beacon.state.Globals
import app.beacon.ui.components.option.Item
import app.beacon.ui.router.Settings

@Composable fun Network(navController: NavController) {
    val context = LocalContext.current
    val pref = context.getSharedPreferences(PreferenceKeys.Network.NAME, Context.MODE_PRIVATE)
    val keyboard = LocalSoftwareKeyboardController.current

    val servicePortFocusRequest = remember { FocusRequester() }
    val serviceHostFocusRequest = remember { FocusRequester() }
    val serviceTimeoutFocusRequest = remember { FocusRequester() }
    var servicePort by remember { mutableStateOf(pref.getInt(PreferenceKeys.Network.DAEMON_PORT , Globals.RuntimeConfig.Network.port ).toString()) }
    var serviceHost by remember { mutableStateOf(pref.getString(PreferenceKeys.Network.DAEMON_BIND_IP ,
        Globals.RuntimeConfig.Network.ip )?: "automatic") }
    var serviceTimeout by remember { mutableStateOf(pref.getInt(PreferenceKeys.Network.DAEMON_NET_TIMEOUT , Globals.RuntimeConfig.Network.timeout ).toString()) }
    var editPort by remember { mutableStateOf(false) }
    var editHost by remember { mutableStateOf(false) }
    var editTimeout by remember { mutableStateOf(false) }

    var buffer = ""

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

        ItemContainer(
            name = "Port number",
            description = "Edit port number"
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().focusRequester(servicePortFocusRequest),
                value = servicePort,
                onValueChange = { i -> if (i.all { it.isDigit() }) servicePort = i },
                label = { Text("Port") },
                placeholder = { Text("between 1025-65536") },
                enabled = editPort,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    pref.edit { putInt(PreferenceKeys.Network.DAEMON_PORT, servicePort.toInt()) }
                    servicePortFocusRequest.freeFocus()
                    editPort = false
                }),
                trailingIcon = {
                    if (!editPort) {
                        IconButton(onClick = {
                            editPort = true;
                            buffer = servicePort;
                            servicePortFocusRequest.captureFocus()
                            servicePortFocusRequest.requestFocus()
                            keyboard?.show()
                        }) {
                            Icon(Icons.Rounded.Edit, contentDescription = null)
                        }
                    } else {
                        IconButton(onClick = {
                            servicePort = buffer
                            servicePortFocusRequest.freeFocus()
                            editPort = false
                        }) {
                            Icon(Icons.Rounded.Close, contentDescription = null, tint = Color.Red.copy(alpha = 0.4f))
                        }
                    }
                }
            )
        }

        ItemContainer(
            name = "Hostname",
            description = "hostname or ip address to bind"
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().focusRequester(serviceHostFocusRequest),
                value = serviceHost,
                onValueChange = { i -> serviceHost = i },
                label = { Text("Address") },
                placeholder = { Text("127.0.0.1") },
                enabled = editHost,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    pref.edit { putString(PreferenceKeys.Network.DAEMON_BIND_IP, serviceHost) }
                    serviceHostFocusRequest.freeFocus()
                    editHost = false
                }),
                trailingIcon = {
                    if (!editHost) {
                        IconButton(onClick = {
                            editHost = true;
                            buffer = serviceHost;
                            serviceHostFocusRequest.captureFocus()
                            serviceHostFocusRequest.requestFocus()
                            keyboard?.show()
                        }) {
                            Icon(Icons.Rounded.Edit, contentDescription = null)
                        }
                    } else {
                        IconButton(onClick = {
                            serviceHost = buffer
                            serviceHostFocusRequest.freeFocus()
                            editHost = false
                        }) {
                            Icon(
                                Icons.Rounded.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onError
                            )
                        }
                    }
                }
            )
        }

        ItemContainer(
            name = "Timeout",
            description = "Connection timeout"
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().focusRequester(serviceTimeoutFocusRequest),
                value = serviceTimeout,
                onValueChange = { i -> if (i.all { it.isDigit() }) serviceTimeout = i },
                label = { Text("Timeout") },
                placeholder = { Text("1000") },
                enabled = editTimeout,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    pref.edit {
                        putInt(
                            PreferenceKeys.Network.DAEMON_NET_TIMEOUT,
                            serviceTimeout.toInt()
                        )
                    }
                    serviceTimeoutFocusRequest.freeFocus()
                    editTimeout = false
                }),
                trailingIcon = {
                    if (!editTimeout) {
                        IconButton(onClick = {
                            editTimeout = true;
                            buffer = servicePort;
                            serviceTimeoutFocusRequest.captureFocus()
                            serviceTimeoutFocusRequest.requestFocus()
                            keyboard?.show()
                        }) {
                            Icon(Icons.Rounded.Edit, contentDescription = null)
                        }
                    } else {
                        IconButton(onClick = {
                            serviceTimeout = buffer
                            serviceTimeoutFocusRequest.freeFocus()
                            editTimeout = false
                        }) {
                            Icon(
                                Icons.Rounded.Close,
                                contentDescription = null,
                                tint = Color.Red.copy(alpha = 0.4f)
                            )
                        }
                    }
                }
            )
        }

        Item(
            name = "Network Interface",
            description = "Select network interface to use",
            onClick = { navController.navigate(Settings._NetworkInterface.name) }
        )
    }
}