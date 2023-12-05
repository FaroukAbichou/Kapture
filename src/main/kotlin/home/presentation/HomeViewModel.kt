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
        )

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Record -> TODO()
            is HomeEvent.RecordSection -> TODO()
            HomeEvent.RecordWithAudio -> TODO()
            HomeEvent.StartRecording -> TODO()
            HomeEvent.StopRecording -> TODO()
        }
    }
}
