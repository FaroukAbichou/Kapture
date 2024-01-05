package record.video.data

import androidx.compose.ui.graphics.ImageBitmap
import com.github.agomezmoron.multimedia.recorder.VideoRecorder
import com.github.agomezmoron.multimedia.recorder.configuration.VideoRecorderConfiguration
import core.util.FileHelper.VideoExtensions
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import core.util.FilePaths
import probe.core.WindowPlacement
import probe.screen.domain.model.Screen
import record.video.domain.VideoRepository
import record.video.domain.model.RecordSettings
import record.video.domain.model.Video
import java.io.File
import java.net.MalformedURLException
import java.nio.file.Path
import kotlin.time.Duration


class VideoRepositoryImpl : VideoRepository {

    override fun startRecording(
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
    ) {
        println("Starting recording screen")
        VideoRecorderConfiguration.wantToUseFullScreen(true)
        VideoRecorderConfiguration.setVideoDirectory(File(FilePaths.VideosPath)) // home
        VideoRecorderConfiguration.setKeepFrames(false)

        try {
            VideoRecorder.start("test")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
    }

    override fun stopRecording() {
        println("Stopping recording")
        VideoRecorder.stop()
    }

    override fun recordScreenWithTimeout(
        duration: Duration,
        config: RecordSettings,
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
    ) {
//        println("Starting timed recording for screen: ${selectedScreen.name}, Duration: ${duration.inWholeSeconds} seconds")
//
//        VideoRecorderConfiguration.wantToUseFullScreen(true);
//        VideoRecorderConfiguration.setVideoDirectory(File(FilePaths.VideosPath)); // home
//        VideoRecorderConfiguration.setKeepFrames(false);
//
//        // Stop recording after the duration has elapsed
//        try {
//            VideoRecorder.start("test")
//            println(videoPath)
//        } catch (e: MalformedURLException) {
//            e.printStackTrace()
//        }
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