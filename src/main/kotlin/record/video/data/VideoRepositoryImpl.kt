package record.video.data

import androidx.compose.ui.graphics.ImageBitmap
import core.util.FileHelper.VideoExtensions
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import org.monte.media.av.Format
import org.monte.media.av.FormatKeys
import org.monte.media.av.codec.video.VideoFormatKeys
import org.monte.media.math.Rational
import org.monte.media.screenrecorder.ScreenRecorder
import probe.core.WindowPlacement
import probe.screen.domain.model.Screen
import record.video.domain.VideoRepository
import record.video.domain.model.RecordSettings
import record.video.domain.model.Video
import java.awt.GraphicsEnvironment
import java.io.File
import java.io.IOException
import java.nio.file.Path
import kotlin.time.Duration

class VideoRepositoryImpl : VideoRepository {
    private var screencorder: Screencorder? = null
    private var screenRecorder: ScreenRecorder? = null
    private val movieFolder: String = System.getProperty("user.home") + File.separator + "Videos"

    init {
        // Make sure the directory exists
        screencorder = Screencorder()
        screencorder
    }
    override fun startRecording(
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
    ) {
        val gc = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .defaultScreenDevice
            .defaultConfiguration

        val fileFormat = Format(
            FormatKeys.MediaTypeKey, FormatKeys.MediaType.FILE,
            FormatKeys.MimeTypeKey, FormatKeys.MIME_AVI
        )
        val screenFormat = Format(
            FormatKeys.MediaTypeKey, FormatKeys.MediaType.VIDEO,
            FormatKeys.EncodingKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
            VideoFormatKeys.CompressorNameKey, VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
            VideoFormatKeys.DepthKey, 24,
            FormatKeys.FrameRateKey, Rational.valueOf(15.0),
            VideoFormatKeys.QualityKey, 1.0f,
            FormatKeys.KeyFrameIntervalKey, (15 * 60)
        )
        val mouseFormat = Format(
            FormatKeys.MediaTypeKey, FormatKeys.MediaType.VIDEO,
            FormatKeys.EncodingKey, ScreenRecorder.ENCODING_BLACK_CURSOR,
            FormatKeys.FrameRateKey, Rational.valueOf(30.0)
        )


        screenRecorder?.start()

    }
    override fun stopRecording() {
        // Stop recording
        try {
            screencorder?.stop()
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle exception, possibly throw a custom exception or log it
        }

        // Clean up
        screencorder = null
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