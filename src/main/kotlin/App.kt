
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import core.theme.KaptureTheme
import home.presentation.HomeViewModel
import home.presentation.component.RecordingFrame
import record.presentation.RecordScreen
import record.presentation.RecordViewModel

@Composable
@Preview
fun App() {
    val recordViewModel = rememberSaveable { RecordViewModel() }
    val recordState = recordViewModel.state.collectAsState()
    KaptureTheme {
        RecordingFrame(
            onEvent = recordViewModel::onRecordingFrameEvent
        )
        RecordScreen(
            state = recordState.value,
            onEvent = recordViewModel::onEvent
        )
//        HomeScreen(
//            state = homeState.value,
//            onEvent = homeViewModel::onEvent
//        )
    }
}