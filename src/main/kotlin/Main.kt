import androidx.compose.ui.window.Window
        import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Screen Recorder"
    ) {
        val config = ConfigurationManager()
        val screenRecorder = ScreenRecorder(config)
        App(screenRecorder)
    }
}