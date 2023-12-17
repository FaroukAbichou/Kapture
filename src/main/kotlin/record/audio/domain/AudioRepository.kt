package record.audio.domain

import probe.domain.model.Screen
import record.audio.domain.model.Audio
import record.video.domain.model.RecordSettings


interface AudioRepository {
    fun getAudioByPath(filePath: String): List<Audio>
    fun recordAudioWithTimeout(config: RecordSettings?)
    fun startAudioRecording(
        config: RecordSettings,
        selectedScreen: Screen
    )
    fun stopAudioRecording()
}