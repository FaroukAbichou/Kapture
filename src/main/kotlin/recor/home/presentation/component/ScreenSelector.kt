package recor.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import recor.home.presentation.state.HomeState
import recor.record.presentation.event.RecordEvent

@Composable
fun ScreenSelector(
    state: HomeState,
    onEvent: (RecordEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(state.screens.size) {
            Button(
                onClick = {
                    onEvent(
                        RecordEvent.SelectScreen(
                            screenId = state.screens[it].id
                        )
                    )
                }
            ) {
                Text("Screen $it")
            }
        }
    }
}