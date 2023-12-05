package home.presentation.event

sealed class HomeEvent {
    data object Record : HomeEvent()
    data class RecordSection(
        val x: Int
    ) : HomeEvent()
    data object RecordWithAudio : HomeEvent()
    data object StartRecording : HomeEvent()
    data object StopRecording : HomeEvent()
}