package recor.record.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import recor.audio.presentation.event.AudioEvent
import recor.audio.presentation.state.AudioState
import recor.image.presentation.event.ImageEvent
import recor.record.presentation.component.RecordContent
import recor.video.presentation.event.VideoEvent
import recor.video.presentation.state.ImageState
import recor.video.presentation.state.VideoState

@Composable
fun RecordScreen(
    videoState: VideoState,
    imageState: ImageState,
    audioState: AudioState,
    onVideoEvent: (VideoEvent) -> Unit,
    onImageEvent: (ImageEvent) -> Unit,
    onAudioEvent: (AudioEvent) -> Unit,
) {
    Scaffold(
        topBar = { },
        bottomBar = { },
        floatingActionButton = { },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize(),
    ) { paddingValues ->
//        if (state.isLoading) {
//            Text("Loading...")
//        } else {
            RecordContent(
                videoState = videoState,
                imageState = imageState,
                audioState = audioState,
                onVideoEvent = onVideoEvent,
                onImageEvent = onImageEvent,
                onAudioEvent = onAudioEvent,
            )
//        }
    }
}