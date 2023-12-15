package record.video.data

//import net.bramp.ffmpeg.FFmpeg
//import net.bramp.ffmpeg.FFmpegExecutor
//import net.bramp.ffmpeg.FFprobe
//import net.bramp.ffmpeg.builder.FFmpegBuilder

import core.util.FFmpegUtils.FFmpegPath
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import core.util.FilePaths
import org.bytedeco.ffmpeg.ffmpeg
import org.bytedeco.ffmpeg.ffprobe
import org.bytedeco.javacpp.Loader
import probe.domain.WindowPlacement
import probe.domain.model.Screen
import record.video.domain.VideoRepository
import record.video.domain.model.RecordSettings
import record.video.domain.model.Video
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.file.Path
import java.util.concurrent.Executors
import java.util.concurrent.Future

class VideoRepositoryImpl : VideoRepository {

    init {
        Loader.load(ffmpeg::class.java)
        Loader.load(ffprobe::class.java)
    }
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

    var ffmpegPath: String = Loader.load(ffmpeg::class.java)


    var ffprobePath: String = Loader.load(ffprobe::class.java)
    private var recordingThread: Future<*>? = null

    private val executorService = Executors.newSingleThreadExecutor()
    private var ffmpegProcess: Process? = null

    override fun recordScreenWithTimeout(
        config: RecordSettings,
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen
    ) {
        recordScreen(config, windowPlacement, selectedScreen)
    }

    fun recordScreen(
        config: RecordSettings,
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen
    ) {
        val cropFilter = createCropFilter(selectedScreen, windowPlacement)
        val pb = ProcessBuilder().apply {
            command(
                FFmpegPath,
                "-f", "avfoundation", // Use avfoundation for macOS screen capture
                "-pix_fmt", "uyvy422", // Use a supported pixel format
                "-i", "0", // Replace with the correct screen capture input
                "-t", config.durationInSeconds.toString(),
                "-r", config.frameRate.toString(),
                "-vcodec", "mpeg4",
                "-vf", cropFilter ?: "", // Apply the crop filter if not null
                config.outputFile + ".mp4"
            )
            if (windowPlacement != null) {
                command().add("-s")
                command().add("${windowPlacement.width}x${windowPlacement.height}")
            }
        }

        // Start the recording in a new thread
        recordingThread = executorService.submit {
            try {
                val process = pb.inheritIO().start()
                process.waitFor()
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error starting FFmpeg process")
            }
        }
    }
//    override fun recordScreenWithTimeout(
//        config: RecordSettings,
//        windowPlacement: WindowPlacement?,
//        selectedScreen: Screen,
//    ) {
//        val cropFilter = createCropFilter(
//            screen = selectedScreen,
//            windowPlacement = windowPlacement,
//        )
//        val builder = FFmpegBuilder()
//            .setInput("1")
//            .setFormat(config.format)
//            .addOutput(config.outputFile)
//            .setDuration(
//                config.durationInSeconds.toLong(),
//                TimeUnit.SECONDS
//            )
//            .setVideoCodec(config.videoCodecName)
//            .setVideoFrameRate(config.frameRate, 1)
//
//            .apply {
//                if (cropFilter != null) {
//                    setVideoFilter(cropFilter)
//                }
//                if (windowPlacement != null) {
//                    setVideoResolution(
//                        windowPlacement.width,
//                        windowPlacement.height
//                    )
//                }
//            }
//            .done()
//
//        recordingThread = executorService.submit {
//            try {
//                executeFFmpegJob(builder)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                println("Error starting FFmpeg process")
//            }
//        }
//    }


    override fun startRecording(
        config: RecordSettings,
        windowPlacement: WindowPlacement,
        selectedScreen: Screen
    ) {
//        val cropFilter = createCropFilter(
//            screen = selectedScreen,
//            windowPlacement = windowPlacement,
//        )
//        val pixelFormat = "uyvy422"
//
//        val ffmpegCommand = mutableListOf(FFmpegUtils.FFmpegPath)
//
//        val builder = FFmpegBuilder()
//            .setInput(selectedScreen.id)
//            .setFormat(config.format)
//            .addOutput(config.outputFile)
//            .setVideoCodec(config.videoCodecName)
//            .setVideoFrameRate(config.frameRate, 1)
//            .addExtraArgs("-pix_fmt", pixelFormat)
//            .apply {
//                if (cropFilter != null) {
//                    setVideoFilter(cropFilter)
//                }
//            }
//            .done()
//
//        ffmpegCommand.addAll(builder.build())
//
//        recordingThread = executorService.submit {
//            try {
//                val processBuilder = ProcessBuilder(ffmpegCommand)
//                processBuilder.directory(File(FilePaths.VideosPath))
//
//                ffmpegProcess = processBuilder.start()
//            } catch (e: Exception) {
//                e.printStackTrace()
//                println("Error starting FFmpeg process")
//            }
//        }
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


//    private fun executeFFmpegJob(builder: FFmpegBuilder) {
//        val executor = FFmpegExecutor(ffmpeg, ffprobe)
//        executor.createJob(builder).run()
//    }

    private fun createCropFilter(
        screen: Screen,
        windowPlacement: WindowPlacement?
    ) = windowPlacement?.let {
        "crop=${it.width}:${it.height}:${
            if (it.x < 0) {
                screen.width + it.x
            } else {
                it.x
            }
        }:${it.y}"
    }

}