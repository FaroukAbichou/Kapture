
import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import java.util.concurrent.TimeUnit


class ScreenRecorder(private val config: ConfigurationManager) {

    fun recordScreen(outputFilePath: String, durationInSeconds: Int) {
        val ffmpeg = FFmpeg("/opt/homebrew/bin/ffmpeg")
        val ffprobe = FFprobe("/opt/homebrew/bin/ffprobe")

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
}

