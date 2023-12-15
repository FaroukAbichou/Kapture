package record.home.presentation.component

import androidx.compose.runtime.Composable
import record.audio.presentation.component.AudiosSection
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState
import record.image.presentation.component.ImagesSection
import record.image.presentation.event.ImageEvent
import record.image.presentation.state.ImageState
import record.video.presentation.component.VideosSection
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun RecordPagesSection(
    selectedSectionIndex: Int,
    videoState: VideoState,
    imageState: ImageState,
    audioState: AudioState,
    onVideoEvent: (VideoEvent) -> Unit,
    onImageEvent: (ImageEvent) -> Unit,
    onAudioEvent: (AudioEvent) -> Unit,
) {
    when (selectedSectionIndex) {
        0 -> GetStartedSection(
            state = videoState,
            onEvent = onVideoEvent
        )

        1 -> VideosSection(
            state = videoState,
            onEvent = onVideoEvent
        )

        2 -> ImagesSection(
            state = imageState,
            onEvent = onImageEvent
        )

        3 -> AudiosSection(
            state = audioState,
            onEvent = onAudioEvent
        )
    }
}