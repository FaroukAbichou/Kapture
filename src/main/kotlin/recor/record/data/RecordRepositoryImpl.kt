package recor.record.data

import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import recor.record.domain.ConfigurationManager
import recor.record.domain.RecordRepository
import probe.domain.Screen
import probe.domain.WindowBounds
import probe.domain.WindowPlacement
import core.util.FFmpegUtils.FFmpegPath
import core.util.FFmpegUtils.FFprobePath
import core.util.FilePaths.VideosPath
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
        val cropFilter = createCropFilter(bounds)

        val builder = FFmpegBuilder()
            .setInput("1")
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
                if (config.windowBounds != null) {
                    setVideoResolution(config.windowBounds.width, config.windowBounds.height)
                }
            }
            .done()

        recordingThread = executorService.submit {
            try {
                executeFFmpegJob(builder)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error starting FFmpeg process")
            }
        }
    }

    override fun recordScreenWithAudio(
        config: ConfigurationManager,
        bounds: WindowBounds?,
        audioSource: String
    ) {
        val cropFilter = createCropFilter(bounds)
        val builder = FFmpegBuilder()
            .setInput("1")
            .setInput(config.audioSource)
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
                if (config.windowBounds != null) {
                    setVideoResolution(config.windowBounds.width, config.windowBounds.height)
                }
            }
            .setAudioCodec("aac")
            .done()

        recordingThread = executorService.submit {
            try {
                executeFFmpegJob(builder)
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error starting FFmpeg process")
            }
        }
    }

    override fun startRecording(
        config: ConfigurationManager,
        bounds: WindowBounds?,
        recordingArea: WindowPlacement,
        selectedScreen: Screen
    ) {
//        val cropFilter = createCropFilter(bounds)
        val cropFilter = createCropFilterWithWindowPlacement(
            screen =selectedScreen,
            recordingArea
        )
        val pixelFormat = "uyvy422"

        val ffmpegCommand = mutableListOf(FFmpegPath)

        val builder = FFmpegBuilder()
            .setInput(selectedScreen.id)
            .setFormat(config.format)
            .addOutput(config.outputFile)
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)
            .addExtraArgs("-pix_fmt", pixelFormat)
            .apply {
                if (cropFilter != null) {
                    setVideoFilter(cropFilter)
                }
            }
            .done()

        ffmpegCommand.addAll(builder.build())

        recordingThread = executorService.submit {
            try {
                val processBuilder = ProcessBuilder(ffmpegCommand)
                processBuilder.directory(File(VideosPath))

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

    private var isRecordingPaused = false
    private var tempFiles = mutableListOf<File>()
    override fun pauseRecording() {
        if (!isRecordingPaused) {
            stopRecordingInternal()
            isRecordingPaused = true
        }
    }

    override fun resumeRecording(config: ConfigurationManager, bounds: WindowBounds?) {
        if (isRecordingPaused) {
            val tempFile = File.createTempFile("recording_", ".mp4", File(VideosPath))
            tempFiles.add(tempFile)

            startRecordingInternal(config, bounds, tempFile.absolutePath)
            isRecordingPaused = false
        }
    }

    private fun startRecordingInternal(config: ConfigurationManager, bounds: WindowBounds?, outputPath: String) {
        val cropFilter = createCropFilter(bounds)
        val pixelFormat = "uyvy422"
        val builder = FFmpegBuilder()
            .setInput(config.screenId)
            .setFormat(config.format)
            .addOutput(outputPath)
            .setVideoCodec(config.videoCodecName)
            .setVideoFrameRate(config.frameRate, 1)
            .addExtraArgs("-pix_fmt", pixelFormat)
            .apply {
                if (cropFilter != null) {
                    setVideoFilter(cropFilter)
                }
                if (config.windowBounds != null) {
                    setVideoResolution(config.windowBounds.width, config.windowBounds.height)
                }
            }
            .done()
        executeFFmpegJob(builder)
    }

    private fun stopRecordingInternal() {
        ffmpegProcess?.let { process ->
            if (process.isAlive) {
                process.outputStream?.let { inputStream ->
                    val writer = OutputStreamWriter(inputStream)
                    writer.write("q")
                    writer.flush()
                    writer.close()
                }
            }
        }
        recordingThread?.cancel(true)
    }

    override fun saveRecording(outputFilePath: String) {
        // Combine all temporary files into the final output file
        // This can be done using FFmpeg's concat demuxer or similar approach
    }

    override fun discardRecording() {
        tempFiles.forEach { it.delete() }
        tempFiles.clear()
        isRecordingPaused = false
    }

    override fun setRecordingArea(position: WindowPlacement) {

    }

    private fun executeFFmpegJob(builder: FFmpegBuilder) {
        val executor = FFmpegExecutor(ffmpeg, ffprobe)
        executor.createJob(builder).run()
    }

    private fun createCropFilter(bounds: WindowBounds?) = bounds?.let {
        "crop=${it.width}:${it.height}:${it.x1}:${it.y1}"
    }

    private fun createCropFilterWithWindowPlacement(
        screen: Screen,
        bounds: WindowPlacement?
    ) = bounds?.let {
        "crop=${it.width}:${it.height}:${
            if (it.x < 0) {
                screen.width + it.x
            } else {
                it.x
            }
        }:${it.y}"
    }
}

