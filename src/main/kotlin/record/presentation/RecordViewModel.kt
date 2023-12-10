package record.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import record.domain.RecordRepository
import record.presentation.event.RecordEvent
import record.presentation.state.RecordState
import screen.domain.ScreenRepository

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
