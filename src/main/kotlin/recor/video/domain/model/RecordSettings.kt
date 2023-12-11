package recor.video.domain.model

import probe.domain.WindowBounds

data class RecordSettings(
    val frameRate: Int = 30,
    val screenId: String = "0",
    val durationInSeconds: Int = 5,
    val audioSource: String = "none",
    val isAudioEnabled: Boolean = false,
    val format: String = "avfoundation",
    val videoCodecName: String = "libx264",
    val outputFile: String = "ScreenRecording.mp4",
    val windowBounds: WindowBounds? = WindowBounds(
        x1 = 0,
        y1 = 0,
        x2 = 720,
        y2 = 720
    )
){
    companion object {
        val default = RecordSettings()
    }
}
