package record.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.SelectOutputLocationSection
import record.settings.presentation.component.SelectRecordingDevice
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
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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