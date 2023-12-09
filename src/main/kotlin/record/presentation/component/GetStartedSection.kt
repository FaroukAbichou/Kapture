package record.presentation.component

import androidx.compose.runtime.Composable
import record.presentation.event.RecordEvent
import record.presentation.state.RecordState

@Composable
fun GetStartedSection(
    state: RecordState,
    onEvent: (RecordEvent) -> Unit
){
    RecordButtonsGrid(
        state = state,
        onEvent = onEvent
    )
}
