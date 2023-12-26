package record.video.domain.model

data class RecordSettings(
    val format: String = "avfoundation",
    val videoCodecName: String = "uyvy422",
    val vcodec : String = "mpeg4"
){
    companion object {
        val default = RecordSettings()
    }
}
