package recor.record.presentation.event

import recor.record.domain.ConfigurationManager
import probe.domain.WindowBounds

sealed class RecordEvent {
    data class RecordSection(
        val config: ConfigurationManager,
        val bounds: WindowBounds?,
    ) : RecordEvent()

    data class Record(
        val config: ConfigurationManager,
    ) : RecordEvent()

    data class RecordWithAudio(
        val config: ConfigurationManager,
        val bounds: WindowBounds?,
        val audioSource: String,
    ) : RecordEvent()

    data class StartRecording(
        val config: ConfigurationManager,
        val bounds: WindowBounds?,
    ) : RecordEvent()

    data class SelectScreen(
        val screenId: String,
    ) : RecordEvent()

    data object StopRecording : RecordEvent()
    data object DiscardRecording : RecordEvent()

    data class SaveRecording(
        val outputFilePath: String,
    ) : RecordEvent()

    data object PauseRecording : RecordEvent()

    data class ResumeRecording(val config: ConfigurationManager, val bounds: WindowBounds?) : RecordEvent()

    data class SetRecordingArea(val bounds: WindowBounds) : RecordEvent()
    class RecordAllWindows(config: ConfigurationManager) : RecordEvent()
    class RecordAudio(config: ConfigurationManager) : RecordEvent()
    data object RecordDevice: RecordEvent()

}
