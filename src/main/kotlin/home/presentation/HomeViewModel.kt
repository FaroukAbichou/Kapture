package home.presentation

import home.presentation.event.HomeEvent
import home.presentation.state.HomeState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import record.domain.RecorderRepository

class HomeViewModel : KoinComponent {

    private val repository: RecorderRepository by inject()

    private val viewModelScope = MainScope()

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeState()
        ).value

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Record -> {
                repository.recordScreen(event.config, bounds = null)
            }

            is HomeEvent.RecordSection -> {
                repository.recordScreen(event.config, event.bounds)
            }

            is HomeEvent.RecordWithAudio -> {
                repository.recordScreenWithAudio(event.config, event.audioSource)
            }

            is HomeEvent.StartRecording -> {
                repository.startRecording(event.config, event.bounds)
            }

            HomeEvent.StopRecording -> {
                repository.stopRecording()
            }
        }
    }
}
