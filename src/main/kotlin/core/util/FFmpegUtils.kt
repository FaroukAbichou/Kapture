package core.util

import java.io.File

object FFmpegUtils {

    val FFmpegPath: String by lazy {
        File(System.getProperty("compose.application.resources.dir"), "ffmpeg").path
    }

    val FFprobePath: String by lazy {
        File(System.getProperty("compose.application.resources.dir"), "ffprobe").path
    }
}