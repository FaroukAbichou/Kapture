import org.bytedeco.ffmpeg.global.avutil

class ConfigurationManager {

    var frameRate: Double = 30.0
    var width: Int = 1280
    var height: Int = 720
    var pixelFormat: Int = avutil.AV_PIX_FMT_UYVY422
    var videoCodecName: String = "libx264"
    var outputFile: String = "output.mp4"

    fun loadConfiguration() {

    }

    fun saveConfiguration() {

    }
}