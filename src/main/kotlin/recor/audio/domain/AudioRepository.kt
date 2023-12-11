package recor.audio.domain

import probe.domain.model.Screen
import recor.audio.domain.model.Audio
import recor.video.domain.model.RecordSettings


interface AudioRepository {
    fun getAudioByPath(filePath: String): List<Audio>
    fun recordAudioWithTimeout(config: RecordSettings?)
    fun startAudioRecording(
        config: RecordSettings,
        selectedScreen: Screen
    )
    fun stopAudioRecording()
}