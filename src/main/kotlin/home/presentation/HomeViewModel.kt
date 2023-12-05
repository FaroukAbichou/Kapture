package home.presentation

import home.presentation.event.HomeEvent
import home.presentation.state.HomeState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import record.domain.RecordRepository
import screen.domain.ScreenRepository

class HomeViewModel : KoinComponent {

    private val recordRepository: RecordRepository by inject()
    private val screenRepository: ScreenRepository by inject()

    private val viewModelScope = MainScope()

    private val _state = MutableStateFlow(HomeState())

    // Expose as a read-only StateFlow
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getScreens()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Record -> {
                recordRepository.recordScreen(event.config, bounds = null)
            }

            is HomeEvent.RecordSection -> {
                recordRepository.recordScreen(event.config, event.bounds)
            }

            is HomeEvent.RecordWithAudio -> {
                recordRepository.recordScreenWithAudio(event.config, event.audioSource)
            }

            is HomeEvent.StartRecording -> {
                recordRepository.startRecording(event.config, event.bounds)
            }

            is HomeEvent.SelectScreen -> selectScreen(event.screenId)

            HomeEvent.StopRecording -> {
                recordRepository.stopRecording()
            }
        }
    }

    private fun selectScreen(screenId: String) {
        val selectedScreen = screenRepository.getScreens().find { it.id == screenId }
            _state.value = _state.value.copy(
                selectedScreen = selectedScreen!!,
            )
    }

    private fun getScreens() {
        _state.value = _state.value.copy(
            screens = screenRepository.getScreens(),
            isLoading = false,
        )
    }
}
