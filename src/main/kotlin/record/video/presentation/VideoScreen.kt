package record.video.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import record.video.presentation.component.VideosSection
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun VideoScreen(
    state: VideoState,
    onEvent: (VideoEvent) -> Unit,
    composeWindow: ComposeWindow,
) {
    Scaffold(
        topBar = {},
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        VideoScreenContent(
            modifier = Modifier.padding(paddingValues),
            composeWindow = composeWindow,
            state = state,
            onEvent = onEvent
        )
    }
}

@Composable
fun VideoScreenContent(
    modifier: Modifier,
    state: VideoState,
    onEvent: (VideoEvent) -> Unit,
    composeWindow: ComposeWindow
){
//    if (state.isLoading){
//        KpProgressIndicator()
//    } else{
        VideosSection(
            modifier = modifier,
            composeWindow = composeWindow,
            state = state,
            onEvent = onEvent
        )
//    }

}