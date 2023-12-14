package record.home.presentation.component

import androidx.compose.runtime.Composable
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
    RecordContent(
        videoState = videoState,
        imageState = imageState,
        audioState = audioState,
        onVideoEvent = onVideoEvent,
        onImageEvent = onImageEvent,
        onAudioEvent = onAudioEvent,
    )

}