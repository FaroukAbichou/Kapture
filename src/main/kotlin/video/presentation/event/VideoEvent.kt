package video.presentation.event

sealed class VideoEvent {
    data class GetVideosByPath(val path: String) : VideoEvent()
}