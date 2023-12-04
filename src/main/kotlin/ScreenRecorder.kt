import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Frame

class ScreenRecorder(private val config: ConfigurationManager) {

    private var recordingThread: Thread? = null
    @Volatile private var isRecording: Boolean = false

    fun startRecording() {
        isRecording = true
        recordingThread = Thread {
            record()
        }
        recordingThread?.start()
    }

    fun stopRecording() {
        isRecording = false
        recordingThread?.join()
    }

    private fun record() {
        val grabber = FFmpegFrameGrabber("1").apply {
            format = "avfoundation"
            imageWidth = config.width
            imageHeight = config.height
            this.frameRate = config.frameRate
            this.pixelFormat = config.pixelFormat
            setOption("probesize", "5000000")
        }

        val recorder = FFmpegFrameRecorder(config.outputFile,config. width,config.height).apply {
            videoCodecName = config.videoCodecName
            this.frameRate = config.frameRate
            pixelFormat = avutil.AV_PIX_FMT_YUV420P
            setVideoOption("crf", "18")
        }

        grabber.start()
        recorder.start()

        var capturedFrame: Frame?
        while (isRecording) {
            capturedFrame = grabber.grab()
            if (capturedFrame != null) {
                recorder.record(capturedFrame)
            }
        }

        recorder.stop()
        recorder.release()
        grabber.stop()
        grabber.release()
    }
}

