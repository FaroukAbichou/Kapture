
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
            .setInput("1") // '1' typically represents the main screen in AVFoundation on macOS
            .setFormat("avfoundation") // Use the AVFoundation format
            .addOutput(outputFilePath) // Specify the output file path
            .setDuration(durationInSeconds.toLong(), TimeUnit.SECONDS) // Set the recording duration
            .setVideoCodec(config.videoCodecName) // Use the x264 codec for video
            .setVideoFrameRate(config.frameRate, 1) // Record at 30 frames per second
            .setVideoResolution(config.width, config.height) // Set the desired resolution
            .done()

        val executor = FFmpegExecutor(ffmpeg, ffprobe)
        executor.createJob(builder).run()
    }
}

