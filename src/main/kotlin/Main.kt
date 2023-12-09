
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import core.di.initKoin
import core.util.FFmpegUtils.FFmpegPath

fun main() {
    initKoin().koin
    application {
        println(FFmpegPath)
        Window(
            onCloseRequest = ::exitApplication,
            title = "Kapture"
        ) {
            App()
        }
    }
}

