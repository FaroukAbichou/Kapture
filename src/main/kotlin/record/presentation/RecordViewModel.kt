package record.presentation

import home.presentation.event.RecordingFrameEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import record.domain.RecordRepository
import record.presentation.event.RecordEvent
import record.presentation.state.RecordState
import screen.domain.Screen
import screen.domain.ScreenRepository
import screen.domain.WindowPlacement

class RecordViewModel : KoinComponent {

    private val recordRepository: RecordRepository by inject()
    private val screenRepository: ScreenRepository by inject()

    private val _state = MutableStateFlow(RecordState())

    val state: StateFlow<RecordState> = _state.asStateFlow()


    fun onEvent(event: RecordEvent) {
        when (event) {
            is RecordEvent.Record -> {
                recordRepository.recordScreen(
                    event.config,
                    bounds = null
                )
            }

            is RecordEvent.RecordSection -> {
                recordRepository.recordScreen(event.config, event.bounds)
            }

            is RecordEvent.RecordWithAudio -> {
                recordRepository.recordScreenWithAudio(
                    event.config,
                    event.bounds,
                    event.audioSource
                )
            }

            is RecordEvent.StartRecording -> {
                recordRepository.startRecording(
                    event.config,
                    event.bounds,
                    selectedScreen = _state.value.selectedScreen,
                    recordingArea = _state.value.recordingArea
                )
            }

            is RecordEvent.SelectScreen -> {
                selectScreen(event.screenId)
            }

            RecordEvent.StopRecording -> {
                recordRepository.stopRecording()
            }

            RecordEvent.DiscardRecording -> {
                recordRepository.discardRecording()
            }

            is RecordEvent.SaveRecording -> {
                recordRepository.saveRecording(event.outputFilePath)
            }

            RecordEvent.PauseRecording -> {
                recordRepository.pauseRecording()
            }

            is RecordEvent.ResumeRecording -> {
                recordRepository.resumeRecording(
                    event.config,
                    event.bounds
                )
            }

            is RecordEvent.SetRecordingArea -> {
//                recordRepository.setRecordingArea(event.bounds)
            }

            is RecordEvent.RecordAllWindows -> {

            }

            is RecordEvent.RecordAudio -> {

            }

            RecordEvent.RecordDevice -> {

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
