
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import core.di.initKoin
import core.theme.KaptureTheme
import javafx.application.Application
import javafx.application.Platform
import record.video.data.YourApp

fun main() {
    initKoin().koin
    application {
        val windowState = rememberWindowState(
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )
        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Kapture"
        ) {
            KaptureTheme {
                KpMainContent()
            }
        }
    }
    Platform.startup {
        // Initialize JavaFX
        Application.launch(YourApp::class.java)
    }
}