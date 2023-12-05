import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import record.data.RecorderRepositoryImpl
import home.presentation.HomeScreen
import home.presentation.HomeViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val viewModel = HomeViewModel()
        HomeScreen(
            state = HomeState(),
            event = HomeEvent(),
        )
    }
}

