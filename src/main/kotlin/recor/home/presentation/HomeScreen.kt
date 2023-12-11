package recor.home.presentation

import androidx.compose.runtime.Composable
import recor.audio.presentation.event.AudioEvent
import recor.audio.presentation.state.AudioState
import recor.home.presentation.component.HomeContent
import recor.image.presentation.event.ImageEvent
import recor.image.presentation.state.ImageState
import recor.video.presentation.event.VideoEvent
import recor.video.presentation.state.VideoState

@Composable
fun HomeScreen(
    videoState: VideoState,
    imageState: ImageState,
    audioState: AudioState,
    onVideoEvent: (VideoEvent) -> Unit,
    onImageEvent: (ImageEvent) -> Unit,
    onAudioEvent: (AudioEvent) -> Unit,
) {
        HomeContent(
            videoState = videoState,
            imageState = imageState,
            audioState = audioState,
            onVideoEvent =onVideoEvent,
            onImageEvent =onImageEvent,
            onAudioEvent =onAudioEvent,
        )
}