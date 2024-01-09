package record.video.data

import androidx.compose.ui.graphics.ImageBitmap
import core.util.FileHelper.VideoExtensions
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import probe.core.WindowPlacement
import probe.screen.domain.model.Screen
import record.video.JpegImagesToMovie
import record.video.domain.VideoRepository
import record.video.domain.model.RecordSettings
import record.video.domain.model.Video
import java.nio.file.Path
import java.util.*
import javax.media.MediaLocator
import kotlin.time.Duration

class VideoRepositoryImpl(
    val screencorder: JpegImagesToMovie?,
) : VideoRepository {
    private var recordingThread: Thread? = null

    override fun startRecording(windowPlacement: WindowPlacement?, selectedScreen: Screen) {
        // Initialize screencorder with necessary parameters
        // Example parameters - modify as needed
        val width = 640  // Example width
        val height = 480 // Example height
        val frameRate = 30 // Example frame rate
        val outputMediaLocator = MediaLocator("file:///path/to/output.mov") // Output file path
        val inputFiles = Vector<String>() // Add paths to your JPEG files here

        // Start recording in a separate thread
        recordingThread = Thread {
            screencorder?.doIt(width, height, frameRate, inputFiles, outputMediaLocator)
        }
        recordingThread?.start()
    }

    override fun stopRecording() {
        // Stop the recording
        // You need to implement a way to stop the recording in your JpegImagesToMovie class
        // This could be a method that stops the processor and signals the end of the recording
//        screencorder?.stopRecording()

        // Join the thread to ensure complete shutdown of recording process
        try {
            recordingThread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun recordScreenWithTimeout(
        duration: Duration,
        config: RecordSettings,
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
    ) {
    }

    override fun playVideo(videoPath: String) {
//        videoPlayer.playVideo(videoPath)
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
        return 0.0
    }

    private fun getVideoThumbnail(path: Path, timestamp: String = "00:00:02"): ImageBitmap {
        return ImageBitmap(0, 0)
    }
}