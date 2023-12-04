import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Frame
import java.io.BufferedReader
import java.io.InputStreamReader

class ScreenRecorder(private val config: ConfigurationManager) {

    private var recordingThread: Thread? = null
    @Volatile private var isRecording: Boolean = false

    private var recordingProcess: Process? = null

    fun startRecording(x: Int, y: Int, width: Int, height: Int) {
        val cropFilter = "crop=$width:$height:$x:$y"
        val ffmpegCommand = "ffmpeg -f avfoundation -i \"0:none\" -r ${config.frameRate} -vf \"$cropFilter\" -t 5 -pix_fmt yuv420p ${config.outputFile}"

        val processBuilder = ProcessBuilder(*ffmpegCommand.split(" ").toTypedArray())
        recordingProcess = processBuilder.start()

        // Handle the process's output in separate threads
        Thread {
            BufferedReader(InputStreamReader(recordingProcess!!.inputStream)).use { input ->
                input.lines().forEach { println(it) }
            }
        }.start()

        Thread {
            BufferedReader(InputStreamReader(recordingProcess!!.errorStream)).use { error ->
                error.lines().forEach { System.err.println(it) }
            }
        }.start()
    }

    fun stopRecording() {
        isRecording = false
        recordingThread?.join()
    }

    private fun record() {
        val grabber = FFmpegFrameGrabber("0").apply {
            format = "avfoundation"
            imageWidth = config.width
            imageHeight = config.height
            this.frameRate = config.frameRate
            this.pixelFormat = config.pixelFormat
            setOption("probesize", "5000000")
        }

        val recorder = FFmpegFrameRecorder(
            config.outputFile,
            config. width,
            config.height
        ).apply {
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

