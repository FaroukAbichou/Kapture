package record.home.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState
import record.home.presentation.component.HomeContent
import record.home.presentation.event.HomeEvent
import record.home.presentation.state.HomeState
import record.image.presentation.event.ImageEvent
import record.image.presentation.state.ImageState
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    Scaffold(
        topBar = {},
        bottomBar = {},
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        HomeContent(
            state = state,
            onEvent = onEvent,
        )
    }
}