package record.video.domain.model

data class RecordSettings(
    val frameRate: Int = 30,
    val format: String = "avfoundation",
    val audioSource: String = "none",
    val videoCodecName: String = "uyvy422",
    val vcodec : String = "mpeg4"
){
    companion object {
        val default = RecordSettings()
    }
}
