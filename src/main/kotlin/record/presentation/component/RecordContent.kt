package record.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import record.presentation.event.RecordEvent
import record.presentation.state.RecordState

@Composable
fun RecordContent(
    state: RecordState,
    onEvent: (RecordEvent) -> Unit,
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecordTitlesSection(
            selectedIndex = 0,
            modifier = Modifier
                .padding(16.dp)
        )

        RecordButtonsGrid(
            state = state,
            onEvent = onEvent
        )
    }
}