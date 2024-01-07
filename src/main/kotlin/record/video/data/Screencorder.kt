package record.video.data

import org.monte.media.av.Format
import org.monte.media.av.FormatKeys
import org.monte.media.av.codec.video.VideoFormatKeys
import org.monte.media.math.Rational
import org.monte.media.screenrecorder.ScreenRecorder
import java.awt.AWTException
import java.awt.GraphicsEnvironment
import java.io.File
import java.io.IOException
import java.util.*

class Screencorder {
    private var screenRecorder: ScreenRecorder? = null
    var movieFolder: String? = null

    @Throws(IOException::class, AWTException::class)
    fun start() {
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

        screenRecorder = ScreenRecorder(
            gc, null,
            fileFormat,
            screenFormat,
            mouseFormat,
            null,
            File(movieFolder)
        )

        screenRecorder?.start()
    }

    @Throws(IOException::class)
    fun stop() {
        screenRecorder!!.stop()
    }

    init {
        movieFolder = if (System.getProperty("os.name").lowercase(Locale.getDefault()).startsWith("windows")) {
            System.getProperty("user.home") + File.separator + "Videos"
        } else {
            System.getProperty("user.home") + File.separator + "Movies"
        }

        val movieDir = File(movieFolder)
        if (!movieDir.exists()) {
            movieDir.mkdirs()
        }
    }
}