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
        val grabber = FFmpegFrameGrabber("1").apply {
            format = "avfoundation"
            imageWidth = this@ScreenRecorder.width
            imageHeight = this@ScreenRecorder.height
            this.frameRate = this@ScreenRecorder.frameRate
            this.pixelFormat = this@ScreenRecorder.pixelFormat
            setOption("probesize", "5000000")
        }

        val recorder = FFmpegFrameRecorder(outputFile, width, height).apply {
            videoCodecName = "libx264"
            this.frameRate = frameRate
            pixelFormat = avutil.AV_PIX_FMT_YUV420P
            setVideoOption("crf", "18")
        }
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

