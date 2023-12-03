import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Frame

class ScreenRecorder(
    private val outputFile: String,
    private val width: Int = 1280,
    private val height: Int = 720,
    private val frameRate: Double = 30.0,
    private val pixelFormat: Int = avutil.AV_PIX_FMT_UYVY422
) {
    fun record() {
        val grabber = FFmpegFrameGrabber("1") // "1" usually refers to the main screen
        grabber.setOption("probesize", "5000000")

        grabber.format = "avfoundation"
        grabber.imageWidth = width
        grabber.imageHeight = height
        grabber.frameRate = frameRate

        grabber.pixelFormat = pixelFormat

        // Set up the recorder
        val recorder = FFmpegFrameRecorder(outputFile, grabber.imageWidth, grabber.imageHeight)
        recorder.videoCodecName = "libx264"
        recorder.frameRate = grabber.frameRate
        recorder.pixelFormat = avutil.AV_PIX_FMT_YUV420P // Set pixel format for recording

        recorder.setVideoOption("crf", "18") // Range is 0 (lossless) to 51 (worst)

        // Start recording
        grabber.start()
        recorder.start()

        var capturedFrame: Frame?
        // Record for a specific duration (e.g., 10 seconds)
        val startTime = System.currentTimeMillis()
        while (System.currentTimeMillis() - startTime < 5 * 1000) {
            capturedFrame = grabber.grab()
            if (capturedFrame != null) {
                recorder.record(capturedFrame)
            }
        }

        // Stop and release the resources
        recorder.stop()
        recorder.release()
        grabber.stop()
        grabber.release()
    }
}

