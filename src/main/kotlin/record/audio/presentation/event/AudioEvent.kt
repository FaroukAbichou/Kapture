package record.audio.presentation.event

sealed class AudioEvent {
    data class GetAudiosByPath(val path: String) : AudioEvent()
}