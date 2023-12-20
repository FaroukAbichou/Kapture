package record.settings.presentation

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import record.settings.presentation.event.SettingsEvent
import record.settings.presentation.state.SettingsState

@Composable
fun SettingsScreen(
    state : SettingsState,
    onEvent : (SettingsEvent) -> Unit,
){

    Button(
        onClick = {
            onEvent(SettingsEvent.NavigateToHome)
        }
    ){
        Text(
            "Navigate To home"
        )
    }

}