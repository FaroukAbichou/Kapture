package recor.video.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import recor.video.data.VideoRepository
import recor.video.presentation.event.VideoEvent
import recor.video.presentation.state.VideoState

class VideoViewModel : KoinComponent {

    private val videoRepository: VideoRepository by inject()

    private val _state = MutableStateFlow(VideoState())
    val state: StateFlow<VideoState> = _state.asStateFlow()

    fun onEvent(event: VideoEvent) {
        when (event) {
            is VideoEvent.GetVideosByPath -> {
                _state.value = _state.value.copy(
                    videos = videoRepository.getVideosByPath(event.path)
                )
                println(_state.value.videos)
            }
        }
    }
}