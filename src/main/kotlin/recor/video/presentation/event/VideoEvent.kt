package recor.video.presentation.event

import probe.domain.WindowBounds
import recor.video.domain.model.RecordSettings

sealed class VideoEvent {
    data class GetVideosByPath(val path: String) : VideoEvent()

    //
    data class RecordSection(
        val config: RecordSettings,
        val bounds: WindowBounds?,
    ) : VideoEvent()

    data class Record(
        val config: RecordSettings,
    ) : VideoEvent()

    data class StartRecording(
        val config: RecordSettings,
        val bounds: WindowBounds?,
    ) : VideoEvent()

    data class SelectScreen(
        val screenId: String,
    ) : VideoEvent()

    data object StopRecording : VideoEvent()
    data object DiscardRecording : VideoEvent()

    data class SaveRecording(
        val outputFilePath: String,
    ) : VideoEvent()

    data object PauseRecording : VideoEvent()

    data class ResumeRecording(val config: RecordSettings, val bounds: WindowBounds?) : VideoEvent()

    data class SetRecordingArea(val bounds: WindowBounds) : VideoEvent()
    class RecordAllWindows(config: RecordSettings) : VideoEvent()
    class RecordAudio(config: RecordSettings) : VideoEvent()
    data object RecordDevice: VideoEvent()

}