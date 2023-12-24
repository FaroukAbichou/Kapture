package record.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    Scaffold(
        topBar = {},
        bottomBar = {},
        containerColor = MaterialTheme.colorScheme.background,
    ) {

        SelectOutputLocationSection(
            folderLocation = {
                onEvent(SettingsEvent.SelectOutputLocation(it))
            },
            currentLocation = state.outputLocation,
            modifier = Modifier
                .padding(16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ) {

            Column(
                modifier = Modifier
            ){
                state.screens.forEach {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier,
                    )
                }
            }

            Column(
                modifier = Modifier
            ){
                state.cameras.forEach {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier,
                    )
                }
            }

            Column(
                modifier = Modifier
            ){
                state.audioSources.forEach {
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}