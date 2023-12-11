package recor.audio.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import recor.audio.domain.AudioRepository
import recor.audio.presentation.event.AudioEvent
import recor.audio.presentation.state.AudioState

class AudioViewModel : KoinComponent {

    private val audioRepository: AudioRepository by inject()

    private val _state = MutableStateFlow(AudioState())
    val state: StateFlow<AudioState> = _state.asStateFlow()

    fun onEvent(event: AudioEvent) {
        when (event) {
            is AudioEvent.GetAudiosByPath -> {
                _state.value = _state.value.copy(
                    audios = audioRepository.getAudioByPath(event.path)
                )
            }
        }
    }
}