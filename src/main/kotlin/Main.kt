import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.di.initKoin

fun main() {
    initKoin().koin
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Screen Recorder"
        ) {
            App()
        }
    }
}