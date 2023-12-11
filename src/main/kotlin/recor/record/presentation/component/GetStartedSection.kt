package recor.record.presentation.component

import androidx.compose.runtime.Composable
import recor.record.presentation.event.RecordEvent
import recor.record.presentation.state.RecordState

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
