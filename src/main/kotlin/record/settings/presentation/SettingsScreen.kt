package record.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    devices : List<Device>
) {

    Column(
        modifier = Modifier.padding(24.dp)
    ) {
        Text(
            text = devices.first().javaClass.simpleName + "s",
            modifier = Modifier,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        devices.forEach {
            Text(
                text = it.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier,
            )
        }
    }

}
