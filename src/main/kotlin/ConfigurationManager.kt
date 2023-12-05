
data class ConfigurationManager(
    val frameRate: Int = 30,
    val durationInSeconds: Int = 10,
    val videoCodecName: String = "libx264",
    val outputFile: String = "output.mp4",
    val screenId: Int = 1,
    val format: String = "avfoundation"
)