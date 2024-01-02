
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import core.di.initKoin
import core.theme.KaptureTheme
import javafx.stage.Stage
import record.video.data.player.Main

fun main() {
    initKoin().koin
    Main().start(Stage())
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
}