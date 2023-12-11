package recor.video.presentation.event

import probe.domain.WindowPlacement
import recor.video.domain.model.RecordSettings

sealed class VideoEvent {
    data class GetVideosByPath(val path: String) : VideoEvent()

    data class RecordSection(
        val config: RecordSettings,
        val windowPlacement: WindowPlacement?,
    ) : VideoEvent()

    data class Record(
        val config: RecordSettings,
        val windowPlacement: WindowPlacement?,
    ) : VideoEvent()

    data class StartRecording(
        val config: RecordSettings,
        val bounds: WindowPlacement,
    ) : VideoEvent()

    data class SelectScreen(
        val screenId: String,
    ) : VideoEvent()

    data object StopRecording : VideoEvent()


    class RecordAllWindows(config: RecordSettings) : VideoEvent()
    class RecordAudio(config: RecordSettings) : VideoEvent()
    data object RecordDevice: VideoEvent()

}