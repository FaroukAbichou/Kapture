package home.presentation.event

import record.domain.ConfigurationManager
import screen.domain.WindowBounds

sealed class HomeEvent {
    data class RecordSection(
        val config: ConfigurationManager, val bounds: WindowBounds?
    ) : HomeEvent()
    data class Record(val config: ConfigurationManager) : HomeEvent()
    data class RecordWithAudio(val config: ConfigurationManager, val audioSource: String) : HomeEvent()
    data class StartRecording(val config: ConfigurationManager, val bounds: WindowBounds?) : HomeEvent()
    data class SelectScreen(val screenId: String) : HomeEvent()
    data object StopRecording : HomeEvent()
}