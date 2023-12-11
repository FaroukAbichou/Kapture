package recor.video.data

import core.util.FFmpegUtils
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import core.util.FilePaths
import net.bramp.ffmpeg.FFmpeg
import net.bramp.ffmpeg.FFmpegExecutor
import net.bramp.ffmpeg.FFprobe
import net.bramp.ffmpeg.builder.FFmpegBuilder
import probe.domain.WindowBounds
import probe.domain.WindowPlacement
import probe.domain.model.Screen
import recor.video.domain.VideoRepository
import recor.video.domain.model.RecordSettings
import recor.video.domain.model.Video
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.file.Path
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class VideoRepositoryImpl : VideoRepository {

    override fun getVideosByPath(filePath: String): List<Video> {
        val videos = getFilesWithExtension(filePath, listOf("mp4", "mkv", "avi", "mov"))

        return videos.map { path ->
            Video(
                name = path.fileName.toString(),
                path = path.toString(),
                size = getFileSize(path),
                date = getFileDate(path),
                duration = getVideoDuration(path),
                thumbnail = getVideoThumbnail(path)
            )
        }
    }

    private fun getVideoDuration(path: Path): String {
        val command = arrayOf("/bin/sh", "-c", "ffmpeg -i \"${path.toAbsolutePath()}\" 2>&1 | grep Duration")
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))

        val durationLine = reader.readLine() ?: return "Unknown duration"

        return durationLine.substringAfter("Duration: ").substringBefore(",").trim()
    }

    private fun getVideoThumbnail(path: Path, timestamp: String = "00:00:02"): String {
        val outputFilePath = "${FilePaths.VideosPath}/${path.fileName.toString().replace(".mp4", ".jpg")}"
        val command = "ffmpeg -i \"${path.toAbsolutePath()}\" -ss $timestamp -vframes 1 \"$outputFilePath\""

        try {
            val process = Runtime.getRuntime().exec(command)
            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

        return if (File(outputFilePath).exists()) outputFilePath else ""
    }



    private val ffmpeg = FFmpeg(FFmpegUtils.FFmpegPath)
    private val ffprobe = FFprobe(FFmpegUtils.FFprobePath)

    private var recordingThread: Future<*>? = null
    private val executorService = Executors.newSingleThreadExecutor()
    private var ffmpegProcess: Process? = null

    override fun recordScreenWithTimeout(
        config: RecordSettings,
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


    override fun startRecording(
        config: RecordSettings,
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

        val ffmpegCommand = mutableListOf(FFmpegUtils.FFmpegPath)

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
                processBuilder.directory(File(FilePaths.VideosPath))

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

    override fun resumeRecording(config: RecordSettings, bounds: WindowBounds?) {
        if (isRecordingPaused) {
            val tempFile = File.createTempFile("recording_", ".mp4", File(FilePaths.VideosPath))
            tempFiles.add(tempFile)

            startRecordingInternal(config, bounds, tempFile.absolutePath)
            isRecordingPaused = false
        }
    }

    private fun startRecordingInternal(config: RecordSettings, bounds: WindowBounds?, outputPath: String) {
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