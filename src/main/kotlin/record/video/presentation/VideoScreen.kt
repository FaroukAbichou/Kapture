package record.video.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
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
        topBar = {},
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        VideoScreenContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent
        )
    }
}

@Composable
fun VideoScreenContent(
    modifier: Modifier,
    state: VideoState,
    onEvent: (VideoEvent) -> Unit
){
    if (state.isLoading){
        CircularProgressIndicator()
    } else{
        VideosSection(
            modifier = modifier,
            state = state,
            onEvent = onEvent
        )

    }

}