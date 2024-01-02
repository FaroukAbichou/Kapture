package record.video.presentation

import core.util.FilePaths
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import probe.audiosource.domain.AudioSourceRepository
import probe.camera.domain.CameraRepository
import probe.core.WindowPlacement
import probe.screen.domain.ScreenRepository
import probe.screen.domain.model.Screen
import record.home.presentation.event.RecordingFrameEvent
import record.video.domain.VideoRepository
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState
import kotlin.time.Duration.Companion.minutes

class VideoViewModel : KoinComponent {

    private val videoRepository: VideoRepository by inject()
    private val cameraRepository: CameraRepository by inject()
    private val screenRepository : ScreenRepository by inject()
    private val audioSourceRepository : AudioSourceRepository by inject()

    private val _state = MutableStateFlow(VideoState())
    val state: StateFlow<VideoState> = _state.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        createDirectory()
        getScreens()
        getVideosByPath()
    }

    fun onEvent(event: VideoEvent) {
        when (event) {
            is VideoEvent.Record -> {
                videoRepository.startRecording(
                    windowPlacement = null,
                    selectedScreen = _state.value.selectedScreen,
                )
            }

            is VideoEvent.RecordSection -> {
                videoRepository.recordScreenWithTimeout(
                    duration = 5.minutes,
                    config = event.config,
                    windowPlacement = event.windowPlacement,
                    selectedScreen = _state.value.selectedScreen,
                )
            }

            is VideoEvent.StartRecording -> {
//                videoRepository.startRecording(
//                    windowPlacement = event.bounds,
//                    selectedScreen = _state.value.selectedScreen,
//                )
                videoRepository.playVideo(
                    videoPath = "${FilePaths.VideosPath}/ScreenRecording 2023-12-18 at 07.27.54 AM.mp4"
                )
            }

            VideoEvent.StopRecording -> {
                videoRepository.stopRecording()
            }

            is VideoEvent.SelectScreen -> {
                selectScreen(
                    screenId = event.screenId
                )
            }

            is VideoEvent.RecordAllWindows -> {

            }

            VideoEvent.RecordDevice -> {

            }

            VideoEvent.SelectScreenSection -> {
                _state.value = _state.value.copy(
                    isRecordSection = true,
                )
            }

            is VideoEvent.GetVideosByPath -> {
                getVideosByPath(event.path)
            }

            is VideoEvent.ChangeVideosLocation -> {
                _state.value = _state.value.copy(
                    outputLocation = event.path,
                )
            }

            is VideoEvent.DeleteVideo -> {

            }

            is VideoEvent.SelectVideo -> {

            }
        }
    }

    private fun getVideosByPath(path: String = FilePaths.VideosPath) {
        _state.value = _state.value.copy(
            isLoading = true
        )
        coroutineScope.launch {
            videoRepository.getVideosByPath(path)
                .onSuccess {
                    _state.value = _state.value.copy(
                        videos = it,
                        isLoading = false
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
        }

    }

    fun onRecordingFrameEvent(event: RecordingFrameEvent) {
        when (event) {
            is RecordingFrameEvent.UpdateWindowPlacement -> {
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
            }
        }
    }

    private fun createDirectory() {
//        probRepository.createDirectoriesIfNotExist()
    }

    private fun selectScreen(screenId: String) {
        _state.value.screens.find { screen ->
            screen.id == screenId
        }?.let {
            _state.value = _state.value.copy(
                selectedScreen = it,
            )
        }
    }

    private fun getScreens() {
        _state.value = _state.value.copy(
            screens = screenRepository.getScreens(),
        )
    }

}