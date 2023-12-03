import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Frame

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
    val outputFile = "output.mp4"
    // Set up the grabber for macOS using AVFoundation

    // Set up the grabber for macOS using AVFoundation
    val grabber = FFmpegFrameGrabber("1") // "1" usually refers to the main screen
    grabber.setOption("probesize", "5000000");

    grabber.format = "avfoundation"
    grabber.imageWidth = 1280
    grabber.imageHeight = 720
    grabber.frameRate = 30.0

    grabber.pixelFormat = avutil.AV_PIX_FMT_UYVY422 // Update pixel format here

    // Set up the recorder
    val recorder = FFmpegFrameRecorder(outputFile, grabber.imageWidth, grabber.imageHeight)
    recorder.videoCodecName = "libx264"
    recorder.frameRate = grabber.frameRate
    recorder.pixelFormat = avutil.AV_PIX_FMT_YUV420P // Set pixel format for recording

    recorder.setVideoOption("crf", "18"); // Range is 0 (lossless) to 51 (worst)

    // Start recording
    grabber.start()
    recorder.start()

    var capturedFrame: Frame?
    // Record for a specific duration (e.g., 10 seconds)
    // Record for a specific duration (e.g., 10 seconds)
    val startTime = System.currentTimeMillis()
    while (System.currentTimeMillis() - startTime < 5 * 1000) {
        capturedFrame = grabber.grab()
        if (capturedFrame != null) {
            recorder.record(capturedFrame)
        }
    }

    // Stop and release the resources

    // Stop and release the resources
    recorder.stop()
    recorder.release()
    grabber.stop()
    grabber.release()
}