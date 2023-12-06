package record.domain

import screen.domain.WindowBounds
import util.FilePaths.VideosPath

data class ConfigurationManager(
    val frameRate: Int = 30,
    val durationInSeconds: Int = 5,
    val videoCodecName: String = "libx264",
    val outputFile: String = "$VideosPath/output.mp4",
    val screenId: String = "1",
    val format: String = "avfoundation",
    val isAudioEnabled: Boolean = false,
    val windowBounds: WindowBounds? = WindowBounds(
        x1 = 0,
        y1 = 0,
        x2 = 1920,
        y2 = 1080
    )
)