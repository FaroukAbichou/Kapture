
import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import util.FFmpegUtils.FFmpegPath
import util.FFmpegUtils.FFprobePath
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit


class ScreenRecorder :IScreenRecorder {
    private val ffmpeg = FFmpeg(FFmpegPath)
    private val ffprobe = FFprobe(FFprobePath)
    private var recordingThread: Future<*>? = null
    private val executorService = Executors.newSingleThreadExecutor()

    override fun recordScreen(
        config: ConfigurationManager,
        bounds: WindowBounds?
    ) {
        val cropFilter = bounds?.let {
            "crop=${it.width}:${it.height}:${it.x1}:${it.y1}"
        }

        val builder = FFmpegBuilder()
            .setInput(config.screenId.toString())
            .setFormat(config.format)
            .addOutput(config.outputFile)
            .setDuration(config.durationInSeconds.toLong(), TimeUnit.SECONDS)
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)
            .setVideoResolution(config.width, config.height)
            .apply { if (cropFilter != null) setVideoFilter(cropFilter) }
            .done()

        val executor = FFmpegExecutor(ffmpeg, ffprobe)
        executor.createJob(builder).run()
    }
    override fun recordScreenWithAudio(
        config: ConfigurationManager,
        audioSource: String
    ) {
        val builder = FFmpegBuilder()
            .setInput(config.screenId.toString())
            .setInput(audioSource) // Specify audio source
            .setFormat(config.format)
            .addOutput(config.outputFile)
            .setDuration(config.durationInSeconds.toLong(), TimeUnit.SECONDS)
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)
            .setVideoResolution(config.width, config.height)
            .setAudioCodec("aac")
            .done()

        val executor = FFmpegExecutor(ffmpeg, ffprobe)
        executor.createJob(builder).run()
    }

    override fun startRecording(
        config: ConfigurationManager,
        bounds: WindowBounds?
    ) {
        val cropFilter = bounds?.let {
            "crop=${it.width}:${it.height}:${it.x1}:${it.y1}"
        }

        val builder = FFmpegBuilder()
            .setInput(config.screenId.toString())
            .setFormat(config.format)
            .addOutput(config.outputFile)
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)
            .setVideoResolution(config.width, config.height)
            .apply { if (cropFilter != null) setVideoFilter(cropFilter) }
            .done()

        recordingThread = executorService.submit {
            val executor = FFmpegExecutor(ffmpeg, ffprobe)
            executor.createJob(builder).run()
        }
    }

    override fun stopRecording() {
        recordingThread?.cancel(true)
    }

    override fun pauseRecording() {
        TODO("Not yet implemented")
    }

    override fun resumeRecording() {
        TODO("Not yet implemented")
    }

    override fun saveRecording(outputFilePath: String) {
        TODO("Not yet implemented")
    }

    override fun discardRecording() {
        TODO("Not yet implemented")
    }

    override fun setRecordingArea(bounds: WindowBounds) {
        TODO("Not yet implemented")
    }
}

