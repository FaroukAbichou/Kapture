package recor.video.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import probe.domain.model.Screen
import probe.domain.ScreenRepository
import probe.domain.WindowPlacement
import recor.home.presentation.event.RecordingFrameEvent
import recor.video.domain.VideoRepository
import recor.video.presentation.event.VideoEvent
import recor.video.presentation.state.VideoState

class VideoViewModel : KoinComponent {

    private val videoRepository: VideoRepository by inject()
    private val screenRepository: ScreenRepository by inject()

    private val _state = MutableStateFlow(VideoState())
    val state: StateFlow<VideoState> = _state.asStateFlow()

    fun onEvent(event: VideoEvent) {
        when (event) {
            is VideoEvent.Record -> {
                videoRepository.recordScreen(
                    event.config,
                    bounds = null
                )
            }

            is VideoEvent.RecordSection -> {
                videoRepository.recordScreen(event.config, event.bounds)
            }

            is VideoEvent.RecordWithAudio -> {
                videoRepository.recordScreenWithAudio(
                    event.config,
                    event.bounds,
                    event.audioSource
                )
            }

            is VideoEvent.StartRecording -> {
                videoRepository.startRecording(
                    event.config,
                    event.bounds,
                    selectedScreen = _state.value.selectedScreen,
                    recordingArea = _state.value.recordingArea
                )
            }

            is VideoEvent.SelectScreen -> {
                selectScreen(event.screenId)
            }

            VideoEvent.StopRecording -> {
                videoRepository.stopRecording()
            }

            VideoEvent.DiscardRecording -> {
                videoRepository.discardRecording()
            }

            is VideoEvent.SaveRecording -> {
                videoRepository.saveRecording(event.outputFilePath)
            }

            VideoEvent.PauseRecording -> {
                videoRepository.pauseRecording()
            }

            is VideoEvent.ResumeRecording -> {
                videoRepository.resumeRecording(
                    event.config,
                    event.bounds
                )
            }

            is VideoEvent.SetRecordingArea -> {
//                videoRepository.setRecordingArea(event.bounds)
            }

            is VideoEvent.RecordAllWindows -> {

            }

            is VideoEvent.RecordAudio -> {

            }

            VideoEvent.RecordDevice -> {

            }

            is VideoEvent.GetVideosByPath -> {
                videoRepository.getVideosByPath(event.path)
            }
        }
    }

    fun onRecordingFrameEvent(event: RecordingFrameEvent) {
        when (event) {
            is RecordingFrameEvent.UpdateWindowPlacement -> {
                videoRepository.setRecordingArea(
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