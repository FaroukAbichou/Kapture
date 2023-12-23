package record.video.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import record.video.presentation.component.VideosSection
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun VideoScreen(
    state: VideoState,
    onEvent: (VideoEvent) -> Unit,
) {
    Scaffold(
        topBar = {
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        VideosSection(
            modifier = Modifier
                .padding(paddingValues),
            state = state,
            onEvent = onEvent
        )
    }
}
