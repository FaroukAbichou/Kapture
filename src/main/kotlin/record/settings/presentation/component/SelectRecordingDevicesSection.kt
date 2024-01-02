package record.settings.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.SelectOutputFolderSection
import record.settings.presentation.event.SettingsEvent
import record.settings.presentation.state.SettingsState

@Composable
fun SelectRecordingDevicesSection(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
) {
    SelectOutputFolderSection(
        onSelectLocation = {
            onEvent(SettingsEvent.SelectOutputLocation(it))
        },
        currentLocation = state.outputLocation,
        modifier = Modifier
            .padding(16.dp)
    )

    SelectRecordingDevice(
        selectedDevice = state.selectedDevicesState.selectedScreen ?: state.screens.first(),
        devices = state.screens,
        onSelectDevice = { device ->
            onEvent(SettingsEvent.SelectDevice(device))
        }
    )
    if (state.cameras.isNotEmpty()){
        SelectRecordingDevice(
            selectedDevice = state.selectedDevicesState.selectedCamera ?: state.cameras.first(),
            devices = state.cameras,
            onSelectDevice = { device ->
                onEvent(SettingsEvent.SelectDevice(device))
            },
        )
    }
    SelectRecordingDevice(
        selectedDevice = state.selectedDevicesState.selectedAudioSource ?: state.audioSources.first(),
        devices = state.audioSources,
        onSelectDevice = { device ->
            onEvent(SettingsEvent.SelectDevice(device))
        },
    )
}