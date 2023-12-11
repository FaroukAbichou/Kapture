package recor.video.presentation.state

import probe.domain.model.Screen
import probe.domain.WindowPlacement
import recor.video.domain.model.Video

data class VideoState(
    val videos : List<Video> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val screens: List<Screen> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
    val recordingArea : WindowPlacement = WindowPlacement.Default,
)
