package home.presentation

import home.presentation.event.HomeEvent
import home.presentation.event.RecordingFrameEvent
import home.presentation.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import record.domain.RecordRepository
import screen.domain.Screen
import screen.domain.ScreenRepository
import screen.domain.WindowPlacement

class HomeViewModel : KoinComponent {

    private val recordRepository: RecordRepository by inject()
    private val screenRepository: ScreenRepository by inject()

    private val _state = MutableStateFlow(HomeState())

    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        getScreens()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Record -> {
                recordRepository.recordScreen(
                    event.config,
                    bounds = null
                )
            }

            is HomeEvent.RecordSection -> {
                recordRepository.recordScreen(event.config, event.bounds)
            }

            is HomeEvent.RecordWithAudio -> {
                recordRepository.recordScreenWithAudio(
                    event.config,
                    event.bounds,
                    event.audioSource
                )
            }

            is HomeEvent.StartRecording -> {
                recordRepository.startRecording(
                    event.config,
                    event.bounds,
                    selectedScreen = _state.value.selectedScreen,
                    recordingArea = _state.value.recordingArea
                )
            }

            is HomeEvent.SelectScreen -> {
                selectScreen(event.screenId)
            }

            HomeEvent.StopRecording -> {
                recordRepository.stopRecording()
            }

            HomeEvent.DiscardRecording -> {
                recordRepository.discardRecording()
            }

            is HomeEvent.SaveRecording -> {
                recordRepository.saveRecording(event.outputFilePath)
            }

            HomeEvent.PauseRecording -> {
                recordRepository.pauseRecording()
            }

            is HomeEvent.ResumeRecording -> {
                recordRepository.resumeRecording(
                    event.config,
                    event.bounds
                )
            }

            is HomeEvent.SetRecordingArea -> {
//                recordRepository.setRecordingArea(event.bounds)
            }

        }
    }

    fun onRecordingFrameEvent(event: RecordingFrameEvent) {
        when (event) {
            is RecordingFrameEvent.UpdateWindowPlacement -> {
                recordRepository.setRecordingArea(
                    position = WindowPlacement(
                        x = event.x,
                        y = event.y,
                        width = event.width,
                        height = event.height
                    )
                )
                _state.value = _state.value.copy(
                    recordingArea = WindowPlacement(
                        x = event.x,
                        y = event.y,
                        width = event.width,
                        height = event.height
                    ),
                    selectedScreen = Screen(
                        id = if (event.x >= 0) "0" else "1",
                        name = "Screen ${if (event.x >= 0) "0" else "1"}",
                        width = _state.value.selectedScreen.width,
                        height = _state.value.selectedScreen.height,
                    ),
                )
                println( event.x)
                println( _state.value.selectedScreen.id)
            }
        }
    }

    private fun selectScreen(screenId: String) {
        val selectedScreen = screenRepository.getScreens().find { it.id == screenId } ?: return
        _state.value = _state.value.copy(
            selectedScreen = selectedScreen,
        )
    }

    private fun getScreens() {
        _state.value = _state.value.copy(
            screens = screenRepository.getScreens(),
            isLoading = false,
        )
    }
}
