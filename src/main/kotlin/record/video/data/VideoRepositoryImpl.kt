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
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.Java2DFrameConverter
import probe.core.WindowPlacement
import probe.screen.domain.model.Screen
import record.video.domain.VideoRepository
import record.video.domain.model.RecordSettings
import record.video.domain.model.Video
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Path
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import kotlin.time.Duration

class VideoRepositoryImpl : VideoRepository {

//    private val videoPlayer = VideoPlayer()

    private var ffmpegProcess: Process? = null
    private var recordingThread: Future<*>? = null
    private val executorService = Executors.newSingleThreadExecutor()

    override fun playVideo(videoPath: String) {
//        videoPlayer.playVideo(videoPath)
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
        val grabber = FFmpegFrameGrabber(path.toFile())
        return try {
            grabber.start()
            val duration = grabber.lengthInTime.toDouble() / 1_000_000 // Convert duration from microseconds to seconds
            grabber.stop()
            duration
        } catch (e: Exception) {
            e.printStackTrace()
            0.0
        }
    }

    private fun getVideoThumbnail(path: Path, timestamp: String = "00:00:02"): ImageBitmap {
        val grabber = FFmpegFrameGrabber(path.toFile())

        return try {
            grabber.start()

            // Convert timestamp to microseconds
            val timestampMicroseconds = timestampToMicroseconds(timestamp)
            grabber.timestamp = timestampMicroseconds

            val frame = grabber.grabImage()
            val converter = Java2DFrameConverter()
            val bufferedImage: BufferedImage = converter.convert(frame)

            grabber.stop()

            bufferedImage.toComposeImageBitmap() // Convert BufferedImage to ImageBitmap
        } catch (e: Exception) {
            e.printStackTrace()
            ImageBitmap(0, 0) // Return an empty ImageBitmap in case of error
        }
    }
    private fun timestampToMicroseconds(timestamp: String): Long {
        // Assuming the timestamp is in the format "HH:mm:ss"
        val parts = timestamp.split(":").map { it.toInt() }
        val hours = parts[0]
        val minutes = parts[1]
        val seconds = parts[2]

        return ((hours * 3600 + minutes * 60 + seconds) * 1_000_000).toLong()
    }
}