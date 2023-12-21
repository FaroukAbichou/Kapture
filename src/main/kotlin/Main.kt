
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import core.di.initKoin
import core.theme.KaptureTheme

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
}