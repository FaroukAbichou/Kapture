package record.video.data

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import core.util.FileHelper.VideoExtensions
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Java2DFrameConverter
import probe.core.WindowPlacement
import probe.screen.domain.model.Screen
import record.video.domain.VideoRepository
import record.video.domain.model.RecordSettings
import record.video.domain.model.Video
import java.awt.image.BufferedImage
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.time.Duration

class VideoRepositoryImpl : VideoRepository {

    private val outputPath = Paths.get("/Users/takiacademy/Kapture/Videos/Recording").toFile()

    private val recorder = FFmpegFrameRecorder(
        /* file = */ outputPath,
        /* imageWidth = */ 1920,
        /* imageHeight = */ 1080
    ).apply {
        format = "mp4"
        videoCodecName = "uyvy422"
        videoCodec = 0
        frameRate = 30.0
        pixelFormat = avutil.AV_PIX_FMT_YUV420P
    }

    override fun startRecording(
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
    ) {
        startScreenRecording()
    }

    override fun stopRecording() {
        try {
            recorder.stop()
            recorder.release()
            println("Recording stopped and resources released.")
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error occurred while stopping the recording: ${e.message}")
        }
    }

    override fun recordScreenWithTimeout(
        duration: Duration,
        config: RecordSettings,
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen,
    ) {
//        startScreenRecording(
//            selectedScreen = selectedScreen,
//            config = config,
//            duration = duration.inWholeSeconds.toString()
//        )
    }

    private fun startScreenRecording() {
        try {
            recorder.start()
            println("Screen recording started: $outputPath")
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error starting screen recording: ${e.message}")
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