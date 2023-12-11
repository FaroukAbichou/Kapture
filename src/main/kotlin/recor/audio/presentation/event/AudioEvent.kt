package recor.audio.presentation.event

sealed class AudioEvent {
    data class GetAudiosByPath(val path: String) : AudioEvent()
}