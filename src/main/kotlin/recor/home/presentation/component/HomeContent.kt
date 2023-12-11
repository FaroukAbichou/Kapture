package recor.home.presentation.component

import androidx.compose.runtime.Composable
import recor.audio.presentation.event.AudioEvent
import recor.audio.presentation.state.AudioState
import recor.image.presentation.event.ImageEvent
import recor.image.presentation.state.ImageState
import recor.record.presentation.component.RecordContent
import recor.video.presentation.event.VideoEvent
import recor.video.presentation.state.VideoState

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