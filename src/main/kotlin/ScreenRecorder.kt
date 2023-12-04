
import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import java.util.concurrent.TimeUnit


class ScreenRecorder(private val config: ConfigurationManager) {
    private val ffmpeg = FFmpeg("/opt/homebrew/bin/ffmpeg")
    private val ffprobe = FFprobe("/opt/homebrew/bin/ffprobe")

    fun recordScreen(outputFilePath: String, durationInSeconds: Int) {

        val builder = FFmpegBuilder()
            .setInput("1")
            .setFormat("avfoundation")
            .addOutput(outputFilePath)
            .setDuration(durationInSeconds.toLong(), TimeUnit.SECONDS)
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)
            .setVideoResolution(config.width, config.height)
            .done()

        val executor = FFmpegExecutor(ffmpeg, ffprobe)
        executor.createJob(builder).run()
    }

    fun recordScreenSection(
        outputFilePath: String,
        durationInSeconds: Int,
        windowBounds: WindowBounds
    ) {

        val builder = FFmpegBuilder()
            .setInput("1")
            .setFormat("avfoundation")
            .addOutput(outputFilePath)
            .setDuration(durationInSeconds.toLong(), TimeUnit.SECONDS)
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)
            .setVideoResolution(config.width, config.height)
            .setVideoFilter("crop=${windowBounds.width}:${windowBounds.height}:${windowBounds.x1}:${windowBounds.y1}")
            .done()

        val executor = FFmpegExecutor(ffmpeg, ffprobe)
        executor.createJob(builder).run()
    }
}

