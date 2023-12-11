package recor.record.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import recor.audio.presentation.event.AudioEvent
import recor.audio.presentation.state.AudioState
import recor.image.presentation.event.ImageEvent
import recor.video.presentation.VideosSection
import recor.video.presentation.event.VideoEvent
import recor.image.presentation.state.ImageState
import recor.video.presentation.state.VideoState

@Composable
fun RecordContent(
    videoState: VideoState,
    imageState: ImageState,
    audioState: AudioState,
    onVideoEvent: (VideoEvent) -> Unit,
    onImageEvent: (ImageEvent) -> Unit,
    onAudioEvent: (AudioEvent) -> Unit,
) {
    var selectedSectionIndex by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecordTitlesSection(
            selectedIndex = { selectedSectionIndex = it },
            modifier = Modifier
                .padding(16.dp)
        )
        RecordPagesSection(
            selectedSectionIndex = selectedSectionIndex,
            videoState = videoState,
            imageState = imageState,
            audioState = audioState,
            onVideoEvent = onVideoEvent,
            onImageEvent = onImageEvent,
            onAudioEvent = onAudioEvent,
        )


    }
}


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