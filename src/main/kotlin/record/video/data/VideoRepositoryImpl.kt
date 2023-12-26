package record.video.data

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
import probe.domain.WindowPlacement
import probe.domain.model.Screen
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

    private var recordingThread: Future<*>? = null

    private val executorService = Executors.newSingleThreadExecutor()
    private var ffmpegProcess: Process? = null

    override fun startRecording(
        config: RecordSettings?,
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
    ) {
        val cropFilter = createCropFilter(selectedScreen, windowPlacement)
        val outputPath = File(
            /* parent = */ FilePaths.VideosPath,
            /* child = */ TimeHelper.getRecordingOutputFileName() + ".mp4"
        ).path

        val configuration = config ?: RecordSettings.default

        val pb = ProcessBuilder().apply {
            command(
                FFmpegPath,
                "-f", configuration.format,
                "-pix_fmt", configuration.videoCodecName,
                "-i", selectedScreen.id,
                "-vcodec", configuration.vcodec,
                outputPath
            )
            if (cropFilter !=null){
                command().add("-vf")
                command().add(cropFilter)
            }
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

    override fun stopRecording() {
        try {
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
        val cropFilter = createCropFilter(selectedScreen, windowPlacement)
        val outputPath = File(
            /* parent = */ FilePaths.VideosPath,
            /* child = */ TimeHelper.getRecordingOutputFileName() + ".mp4"
        ).path

        val pb = ProcessBuilder().apply {
            command(
                FFmpegPath,
                "-f", "avfoundation",
                "-pix_fmt", "uyvy422",
                "-i", "0",
                "-t", duration.inWholeSeconds.toString(),
                "-vcodec", "mpeg4",
                "-vf", cropFilter ?: "",
                outputPath
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

    private fun createCropFilter(
        screen: Screen,
        windowPlacement: WindowPlacement?,
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