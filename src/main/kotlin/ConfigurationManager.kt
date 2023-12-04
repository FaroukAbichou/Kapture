
data class ConfigurationManager(
    val frameRate: Int = 30,
    val width: Int = 1280,
    val height: Int = 720,
    val videoCodecName: String = "libx264",
    val outputFile: String = "output.mp4"
)