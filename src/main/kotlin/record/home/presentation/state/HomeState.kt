package record.home.presentation.state

import probe.domain.WindowPlacement
import probe.domain.model.Screen

data class HomeState (
    val isLoading: Boolean = false,
    val screens: List<Screen> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
    val recordingArea : WindowPlacement = WindowPlacement.Default,
    val outputLocation : String = "",
)
