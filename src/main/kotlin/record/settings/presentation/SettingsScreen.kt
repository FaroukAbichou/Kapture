package record.settings.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.components.SelectOutputLocationSection
import probe.domain.model.Device
import record.settings.presentation.event.SettingsEvent
import record.settings.presentation.state.SettingsState

@Composable
fun SettingsScreen(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
) {
    Scaffold(
        topBar = {},
        bottomBar = {},
        containerColor = MaterialTheme.colorScheme.background,
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            SelectOutputLocationSection(
                folderLocation = {
                    onEvent(SettingsEvent.SelectOutputLocation(it))
                },
                currentLocation = state.outputLocation,
                modifier = Modifier
                    .padding(16.dp)
            )

            SelectRecordingDevice(state.screens)
            SelectRecordingDevice(state.cameras)
            SelectRecordingDevice(state.audioSources)
        }

    }
}

@Composable
fun SelectRecordingDevice(
    devices: List<Device>,
) {
    var selectedDevice by remember { mutableStateOf(devices.first()) }
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = devices.first().javaClass.simpleName + "s",
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        devices.forEach { device ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier,
            ) {
                RadioButton(
                    selected = (device == selectedDevice),
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colors.onPrimaryContainer,
                        unselectedColor = Color.Gray,
//                        disabledSelectedColor = ,
//                        disabledUnselectedColor =
                    ),
                    onClick = { selectedDevice = device }
                )
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }

}
