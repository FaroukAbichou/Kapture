package record.home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState
import record.image.presentation.event.ImageEvent
import record.image.presentation.state.ImageState
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

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