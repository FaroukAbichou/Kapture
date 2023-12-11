package recor.record.presentation.component

import androidx.compose.runtime.Composable
import recor.video.presentation.event.VideoEvent
import recor.video.presentation.state.VideoState

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
