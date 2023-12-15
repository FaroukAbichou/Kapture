package record.home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.SelectOutputLocationSection
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState
import record.image.presentation.event.ImageEvent
import record.image.presentation.state.ImageState
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun HomeContent(
    videoState: VideoState,
    imageState: ImageState,
    audioState: AudioState,
    onVideoEvent: (VideoEvent) -> Unit,
    onImageEvent: (ImageEvent) -> Unit,
    onAudioEvent: (AudioEvent) -> Unit,
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectOutputLocationSection(
            folderLocation = { value -> onVideoEvent(VideoEvent.ChangeVideosLocation(value)) },
            currentLocation = videoState.outputLocation,
            modifier = Modifier
                .padding(16.dp)
        )

        RecordContent(
            videoState = videoState,
            imageState = imageState,
            audioState = audioState,
            onVideoEvent = onVideoEvent,
            onImageEvent = onImageEvent,
            onAudioEvent = onAudioEvent,
        )
    }

}
