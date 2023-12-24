package record.settings.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.SelectOutputLocationSection
import core.util.FilePaths
import record.settings.presentation.event.SettingsEvent
import record.settings.presentation.state.SettingsState

@Composable
fun SettingsScreen(
    state : SettingsState,
    onEvent : (SettingsEvent) -> Unit,
){
    Scaffold(
        topBar = {},
        bottomBar = {},
        containerColor = MaterialTheme.colorScheme.background,
    ) {

        SelectOutputLocationSection(
            folderLocation = {
                onEvent(SettingsEvent.SelectOutputLocation(FilePaths.KapturePath))
            },
            currentLocation = state.outputLocation,
            modifier = Modifier
                .padding(16.dp)
        )
    }

}