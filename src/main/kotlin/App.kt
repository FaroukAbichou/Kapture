import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import core.theme.KaptureTheme
import record.audio.presentation.AudioViewModel
import record.home.presentation.HomeScreen
import record.home.presentation.component.RecordingFrame
import record.home.presentation.event.RecordingFrameEvent
import record.image.presentation.ImageViewModel
import record.video.presentation.VideoViewModel

@Composable
@Preview
fun App() {
    val videoViewModel = rememberSaveable { VideoViewModel() }
    val imageViewModel = rememberSaveable { ImageViewModel() }
    val audioViewModel = rememberSaveable { AudioViewModel() }

    val videoState = videoViewModel.state.collectAsState()
    val imageState = imageViewModel.state.collectAsState()
    val audioState = audioViewModel.state.collectAsState()

    KaptureTheme {
        if (
            videoState.value.isRecordSection ||
            imageState.value.isRecordSection
        ) RecordingFrame(
            modifier = Modifier,
        ) { x: Int, y: Int, height: Int, width: Int ->
            videoViewModel.onRecordingFrameEvent(
                RecordingFrameEvent.UpdateWindowPlacement(
                    x = x, y = y, height = height, width = width,
                )
            )
            imageViewModel.onRecordingFrameEvent(
                RecordingFrameEvent.UpdateWindowPlacement(
                    x = x, y = y, height = height, width = width,
                )
            )
        }

        HomeScreen(
            videoState = videoState.value,
            imageState = imageState.value,
            audioState = audioState.value,
            onVideoEvent = videoViewModel::onEvent,
            onImageEvent = imageViewModel::onEvent,
            onAudioEvent = audioViewModel::onEvent,
        )
    }
}