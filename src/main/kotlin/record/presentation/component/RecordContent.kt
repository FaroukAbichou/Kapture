package record.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
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
    var selectedSectionIndex by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecordTitlesSection(
            selectedIndex = { selectedSectionIndex = it },
            modifier = Modifier
                .padding(16.dp)
        )
        RecordPagesSection(
            selectedSectionIndex = selectedSectionIndex,
            state = state,
            onEvent = onEvent
        )


    }
}


@Composable
fun RecordPagesSection(
    selectedSectionIndex: Int,
    state: RecordState,
    onEvent: (RecordEvent) -> Unit
) {
    when (selectedSectionIndex) {
        0 -> GetStartedSection(
            state = state,
            onEvent = onEvent
        )

        1 -> VideosSection(
            state = state,
            onEvent = onEvent
        )

        2 -> ImagesSection(
            state = state,
            onEvent = onEvent
        )

        3 -> AudiosSection(
            state = state,
            onEvent = onEvent
        )
    }
}