
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import home.data.RecorderRepositoryImpl
import home.presentation.HomeScreen

@Composable
@Preview
fun App(
    recorderRepositoryImpl: RecorderRepositoryImpl
) {
    MaterialTheme {
        HomeScreen(recorderRepositoryImpl)
    }
}

