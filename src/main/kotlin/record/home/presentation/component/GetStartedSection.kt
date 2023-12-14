package record.home.presentation.component

import androidx.compose.runtime.Composable
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun GetStartedSection(
    state: VideoState,
    onEvent: (VideoEvent) -> Unit
){
    RecordButtonsGrid(
        state = state,
        onEvent = onEvent
    )
}
