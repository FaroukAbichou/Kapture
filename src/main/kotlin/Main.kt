import androidx.compose.ui.Alignment
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.window.*
import core.di.initKoin
import core.theme.KaptureTheme

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
            val composeWindow : ComposeWindow = window
            KaptureTheme {
                KpMainContent(
                    composeWindow = composeWindow
                )
            }
        }
    }
}