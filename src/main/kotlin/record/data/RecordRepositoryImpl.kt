package record.data

import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import record.domain.ConfigurationManager
import record.domain.RecordRepository
import screen.domain.WindowBounds
import util.FFmpegUtils.FFmpegPath
import util.FFmpegUtils.FFprobePath
import java.io.File
import java.io.OutputStreamWriter
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class RecordRepositoryImpl : RecordRepository {
    private val ffmpeg = FFmpeg(FFmpegPath)
    private val ffprobe = FFprobe(FFprobePath)

    private var recordingThread: Future<*>? = null
    private val executorService = Executors.newSingleThreadExecutor()
    private var ffmpegProcess: Process? = null

    override fun recordScreen(
        config: ConfigurationManager,
        bounds: WindowBounds?
    ) {
        val cropFilter = bounds?.let {
            "crop=${it.width}:${it.height}:${it.x1}:${it.y1}"
        }

        val builder = FFmpegBuilder()
            .setInput(config.screenId)
            .setFormat(config.format)
            .addOutput(config.outputFile)
            .setDuration(
                config.durationInSeconds.toLong(),
                TimeUnit.SECONDS
            )
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)

            .apply {
                if (cropFilter != null) {
                    setVideoFilter(cropFilter)
                }
                if(config.windowBounds != null) {
                    setVideoResolution(config.windowBounds.width, config.windowBounds.height)
                }
            }
            .done()

        val executor = FFmpegExecutor(ffmpeg, ffprobe)
        executor.createJob(builder).run()
    }

    override fun recordScreenWithAudio(
        config: ConfigurationManager,
        bounds: WindowBounds?,
        audioSource: String
    ) {
        val cropFilter = bounds?.let {
            "crop=${it.width}:${it.height}:${it.x1}:${it.y1}"
        }
        val builder = FFmpegBuilder()
            .setInput(config.screenId)
            .setInput(audioSource)
            .setFormat(config.format)
            .addOutput(config.outputFile)
            .setDuration(
                config.durationInSeconds.toLong(),
                TimeUnit.SECONDS
            )
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)
            .apply {
                if (cropFilter != null) {
                    setVideoFilter(cropFilter)
                }
                if(config.windowBounds != null) {
                    setVideoResolution(config.windowBounds.width, config.windowBounds.height)
                }
            }
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
        val pixelFormat = "uyvy422"

        val ffmpegCommand = mutableListOf(FFmpegPath)

        val builder = FFmpegBuilder()
            .setInput(config.screenId)
            .setFormat(config.format)
            .addOutput(config.outputFile)
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)
            .addExtraArgs("-pix_fmt", pixelFormat)
            .apply {
                if (cropFilter != null) {
                    setVideoFilter(cropFilter)
                }
                if(config.windowBounds != null) {
                    setVideoResolution(config.windowBounds.width, config.windowBounds.height)
                }
            }
            .done()

        ffmpegCommand.addAll(builder.build())

        recordingThread = executorService.submit {
            try {
                val processBuilder = ProcessBuilder(ffmpegCommand)
                processBuilder.directory(File("/Users/takiacademy/Desktop"))

                ffmpegProcess = processBuilder.start()
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error starting FFmpeg process")
            }
        }
    }

    override fun stopRecording() {
        ffmpegProcess?.let { process ->
            if (process.isAlive) {
                process.outputStream?.let { inputStream ->
                    val writer = OutputStreamWriter(inputStream)
                    writer.write("q")
                    writer.flush()
                    writer.close()
                } ?: println("FFmpeg process input stream is null")
            } else {
                println("FFmpeg process is not running")
            }
        } ?: println("FFmpeg process is null")

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

