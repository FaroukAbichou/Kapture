package record.video.presentation.state

import probe.core.WindowPlacement
import probe.screen.domain.model.Screen
import record.video.domain.model.Video

data class VideoState(
    val videos : List<Video> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
    val screens: List<Screen> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
    val recordingArea : WindowPlacement = WindowPlacement.Default,
    val isRecordSection: Boolean = false,
    val outputLocation : String = "",
)