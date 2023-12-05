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
import screen.data.ScreenRepository

class HomeViewModel : KoinComponent {

    private val recorderRepository: RecorderRepository by inject()
    private val screenRepository: ScreenRepository = ScreenRepository()

    private val viewModelScope = MainScope()

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            HomeState()
        ).value

    init {
        getScreensInfo()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Record -> {
                recorderRepository.recordScreen(event.config, bounds = null)
            }

            is HomeEvent.RecordSection -> {
                recorderRepository.recordScreen(event.config, event.bounds)
            }

            is HomeEvent.RecordWithAudio -> {
                recorderRepository.recordScreenWithAudio(event.config, event.audioSource)
            }

            is HomeEvent.StartRecording -> {
                recorderRepository.startRecording(event.config, event.bounds)
            }

            HomeEvent.StopRecording -> {
                recorderRepository.stopRecording()
            }

            is HomeEvent.SelectScreen -> {
                _state.value = _state.value.copy(
                    selectedScreenId = event.screenId
                )
            }
        }
    }

    private fun getScreensInfo() {
        _state.value = _state.value.copy(
            isLoading = true,
        )
        val resolutions = screenRepository.getScreenResolutions()
        val numberOfScreens = screenRepository.getNumberOfScreens()
        println( "resolutions: $resolutions")

        println( "numberOfScreens: $numberOfScreens")
        println( "isLoading: ${_state.value.isLoading}")
        _state.value = _state.value.copy(
            resolutions = resolutions,
            numberOfScreens = numberOfScreens,
            isLoading = false,
        )
        println( "isLoading: ${_state.value.isLoading}")

    }
}
