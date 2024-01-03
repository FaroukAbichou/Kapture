
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import core.di.initKoin
import core.theme.KaptureTheme
import record.video.presentation.component.VideoPlayer

fun main() {
    initKoin().koin
    application(exitProcessOnExit = true) {
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
//                KpMainContent()
                VideoPlayer(
                    composeWindow = window
                )
            }
        }
    }
}