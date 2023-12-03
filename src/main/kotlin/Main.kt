import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.application
import org.bytedeco.javacv.CanvasFrame
import org.bytedeco.javacv.FFmpegFrameGrabber

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

fun main()  {
    val x = 0
    val y = 0
    val w = 1024
    val h = 768 // specify the region of screen to grab
    val grabber = FFmpegFrameGrabber("1")
    grabber.format = "yuyv422"
    grabber.imageWidth = w
    grabber.imageHeight = h
    grabber.start()

    val frame = CanvasFrame("Screen Capture")
    while (frame.isVisible) {
        frame.showImage(grabber.grab())
    }
    frame.dispose()
    grabber.stop()
    println("Done.")
}
