package recor.video.presentation.event

import probe.domain.WindowBounds
import recor.video.domain.model.ConfigurationManager

sealed class VideoEvent {
    data class GetVideosByPath(val path: String) : VideoEvent()

    //
    data class RecordSection(
        val config: ConfigurationManager,
        val bounds: WindowBounds?,
    ) : VideoEvent()

    data class Record(
        val config: ConfigurationManager,
    ) : VideoEvent()

    data class RecordWithAudio(
        val config: ConfigurationManager,
        val bounds: WindowBounds?,
        val audioSource: String,
    ) : VideoEvent()

    data class StartRecording(
        val config: ConfigurationManager,
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

    data class ResumeRecording(val config: ConfigurationManager, val bounds: WindowBounds?) : VideoEvent()

    data class SetRecordingArea(val bounds: WindowBounds) : VideoEvent()
    class RecordAllWindows(config: ConfigurationManager) : VideoEvent()
    class RecordAudio(config: ConfigurationManager) : VideoEvent()
    data object RecordDevice: VideoEvent()

}