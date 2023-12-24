package record.settings.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.SelectOutputLocationSection
import record.settings.presentation.event.SettingsEvent
import record.settings.presentation.state.SettingsState

@Composable
fun SettingsScreen(
    state : SettingsState,
    onEvent : (SettingsEvent) -> Unit,
){

    SelectOutputLocationSection(
        folderLocation = {
//                onEvent(HomeEvent.SelectOutputLocation)
        },
        currentLocation = state.outputLocation,
        modifier = Modifier
            .padding(16.dp)
    )

}