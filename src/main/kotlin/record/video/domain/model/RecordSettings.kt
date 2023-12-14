package record.video.domain.model

import core.util.TimeHelper


data class RecordSettings(
    val frameRate: Int = 30,
    val screenId: String = "0",
    val durationInSeconds: Int = 5,
    val audioSource: String = "none",
    val isAudioEnabled: Boolean = false,
    val format: String = "avfoundation",
    val videoCodecName: String = "libx264",
    val outputFile: String = TimeHelper.getRecordingOutputFileName(),
){
    companion object {
        val default = RecordSettings()
    }
}
