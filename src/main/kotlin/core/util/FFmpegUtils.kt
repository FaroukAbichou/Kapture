package core.util

import java.io.File

object FFmpegUtils {
//    const val FFmpegPath = "/Users/takiacademy/IdeaProjects/Kapture/lib/macos/ffmpeg"

    val FFmpegPath: String by lazy {
        File(System.getProperty("compose.application.resources.dir"), "ffmpeg").path
    }

    val FFprobePath: String by lazy {
        File(System.getProperty("compose.application.resources.dir"), "ffprobe").path
    }
}