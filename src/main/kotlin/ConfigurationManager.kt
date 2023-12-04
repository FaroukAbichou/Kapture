
data class ConfigurationManager(
    val frameRate: Double = 30.0,
    val width: Int = 1280,
    val height: Int = 720,
    val videoCodecName: String = "libx264",
    val outputFile: String = "output.mp4"
)