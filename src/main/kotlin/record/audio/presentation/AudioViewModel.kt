package record.audio.presentation

import core.util.FilePaths
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import record.audio.domain.AudioRepository
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState

class AudioViewModel : KoinComponent {

    private val audioRepository: AudioRepository by inject()

    private val _state = MutableStateFlow(AudioState())
    val state: StateFlow<AudioState> = _state.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        getAudiosByPath(FilePaths.AudiosPath)
    }

    fun onEvent(event: AudioEvent) {
        when (event) {
            is AudioEvent.GetAudiosByPath -> {
                getAudiosByPath(event.path)
            }

            is AudioEvent.DeleteAudio -> {

            }

            AudioEvent.GetAudios -> {

            }

            is AudioEvent.SelectAudio -> {

            }

            is AudioEvent.SelectAudiosLocation -> {

            }
        }
    }

    private fun getAudiosByPath(path: String) {
        _state.value = _state.value.copy(
            isLoading = true,
        )
        coroutineScope.launch {
            audioRepository.getAudioByPath(path)
                .onSuccess {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        audios = it,
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
        }
    }
}