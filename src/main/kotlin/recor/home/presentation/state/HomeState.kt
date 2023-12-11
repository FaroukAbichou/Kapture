package recor.home.presentation.state

import probe.domain.model.Screen
import probe.domain.WindowPlacement

data class HomeState(
    val isLoading: Boolean = false,
    val screens: List<Screen> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
    val recordingArea : WindowPlacement = WindowPlacement.Default,
)
