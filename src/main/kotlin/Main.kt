
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.di.initKoin

fun main() {
    initKoin(enableNetworkLogs = false).koin
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Screen Recorder"
        ) {
            App()
        }
    }
}