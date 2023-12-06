
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import home.presentation.HomeScreen
import home.presentation.HomeViewModel
import home.presentation.component.RecordingFrame

@Composable
@Preview
fun App() {
    val viewModel = rememberSaveable { HomeViewModel() }
    val state = viewModel.state.collectAsState()
    MaterialTheme {
        HomeScreen(
            state = state.value,
            onEvent = viewModel::onEvent
        )
        RecordingFrame(
            onEvent = viewModel::onRecordingFrameEvent
        )
    }
}