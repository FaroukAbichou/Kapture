package record.audio.presentation.event

sealed class AudioEvent {
    data object GetAudios : AudioEvent()
    data class SelectAudio(val path: String) : AudioEvent()
    data class DeleteAudio(val path: String) : AudioEvent()
    data class GetAudiosByPath(val path: String) : AudioEvent()
    data class SelectAudiosLocation(val path: String) : AudioEvent()
}