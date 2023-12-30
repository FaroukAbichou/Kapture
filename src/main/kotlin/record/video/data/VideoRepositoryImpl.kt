package record.video.data

import record.video.data.player.VideoPlayer
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import core.util.FFmpegUtils.FFmpegPath
import core.util.FileHelper.VideoExtensions
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import core.util.FilePaths
import core.util.TimeHelper
import org.jetbrains.skia.Image
import probe.core.WindowPlacement
import probe.screen.domain.model.Screen
import record.video.domain.VideoRepository
import record.video.domain.model.RecordSettings
import record.video.domain.model.Video
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

class VideoRepositoryImpl : VideoRepository {

    private val videoPlayer = VideoPlayer()

    private var ffmpegProcess: Process? = null
    private var recordingThread: Future<*>? = null
    private val executorService = Executors.newSingleThreadExecutor()

    override fun playVideo(videoPath: String) {
        videoPlayer.playVideo(videoPath)
    }

    override fun startRecording(
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
    ) {
        println("Starting recording for screen: ${selectedScreen.name}")
        recordScreenInternal(
            windowPlacement,
            selectedScreen,
            RecordSettings.default,
            duration = null
        )
    }

    override fun stopRecording() {
        try {
            println("Stopping recording.")
            ffmpegProcess?.outputStream?.let {
                it.write("q\n".toByteArray())
                it.flush()
            }
            ffmpegProcess?.waitFor(10, TimeUnit.SECONDS)
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error stopping FFmpeg process")
        } finally {
            ffmpegProcess?.destroy()
            ffmpegProcess = null
        }
    }

    override fun recordScreenWithTimeout(
        duration: Duration,
        config: RecordSettings,
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
    ) {
        println("Starting timed recording for screen: ${selectedScreen.name}, Duration: ${duration.inWholeSeconds} seconds")
        recordScreenInternal(windowPlacement, selectedScreen, config, duration.inWholeSeconds.toString())
    }

    private fun recordScreenInternal(
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
        config: RecordSettings,
        duration: String?,
    ) {

        val outputPath = File(FilePaths.VideosPath, TimeHelper.getRecordingOutputFileName() + ".mp4").path
        val cropFilter = createCropFilter(selectedScreen, windowPlacement)
        println("Recording to file: $outputPath")

        val pb = ProcessBuilder().apply {
            command(buildFFmpegCommand(config, selectedScreen, cropFilter, outputPath, duration, windowPlacement))
        }

        recordingThread = executorService.submit {
            try {
                ffmpegProcess = pb.inheritIO().start().also { it.waitFor() }
                println("Recording completed. File saved at: $outputPath")
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error starting FFmpeg process")
            }
        }
    }

    private fun buildFFmpegCommand(
        config: RecordSettings,
        screen: Screen,
        cropFilter: String?,
        outputPath: String,
        duration: String?,
        windowPlacement: WindowPlacement?,
    ): List<String> {
        return mutableListOf(
            FFmpegPath,
            "-f", config.format,
            "-pix_fmt", config.videoCodecName,
            "-i", screen.id
        ).apply {
            duration?.let { addAll(listOf("-t", it)) }
            add("-vcodec")
            add(config.vcodec)
            cropFilter?.let { addAll(listOf("-vf", it)) }
            windowPlacement?.let { addAll(listOf("-s", "${it.width}x${it.height}")) }
            add(outputPath)
        }
    }

    private fun createCropFilter(
        screen: Screen,
        windowPlacement: WindowPlacement?,
    ): String? = windowPlacement?.let {
        "crop=${it.width}:${it.height}:${maxOf(screen.width + it.x, it.x)}:${it.y}"
    }


    /**
     * Recoded Videos listing
     * */
    override fun getVideosByPath(filePath: String): Result<List<Video>> {
        val videos = getFilesWithExtension(filePath, VideoExtensions)

        return try {
            Result.success(
                videos.map { path ->
                    Video(
                        name = path.fileName.toString(),
                        path = path.toString(),
                        size = getFileSize(path),
                        dateCreated = getFileDate(path),
                        duration = getVideoDuration(path),
                        thumbnail = getVideoThumbnail(path),
                    )
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getVideoDuration(path: Path): Double {
        try {
            val processBuilder = ProcessBuilder(
                "ffprobe",
                "-v", "error",
                "-show_entries", "format=duration",
                "-of", "default=noprint_wrappers=1:nokey=1",
                path.toString()
            )
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()

            BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                val output = reader.readLine()
                process.waitFor()
                return output.toDoubleOrNull() ?: 0.0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0.0
    }

    private fun getVideoThumbnail(path: Path, timestamp: String = "00:00:02"): ImageBitmap {
        val thumbnailPath = Paths.get("thumbnail.png")
        try {
            val processBuilder = ProcessBuilder(
                "ffmpeg",
                "-i", path.toString(),
                "-ss", timestamp,
                "-vframes", "1",
                thumbnailPath.toString()
            )
            val process = processBuilder.start()
            process.waitFor()

            val image = Image.makeFromEncoded(Files.readAllBytes(thumbnailPath))
            return image.toComposeImageBitmap().also {
                Files.deleteIfExists(thumbnailPath)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ImageBitmap(0, 0)// TODO ("Error getting video thumbnail")
    }

}