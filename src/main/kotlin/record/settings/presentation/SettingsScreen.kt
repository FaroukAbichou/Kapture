package record.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.KpProgressIndicator
import record.settings.presentation.component.SelectRecordingDevicesSection
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
        SettingsScreenContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent
        )
    }
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier,
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
) {
    if (state.isLoading) {
        KpProgressIndicator()
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            SelectRecordingDevicesSection(state, onEvent)
        }
    }
}

