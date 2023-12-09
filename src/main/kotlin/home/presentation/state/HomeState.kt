package home.presentation.state

import screen.domain.Screen
import screen.domain.WindowPlacement

data class HomeState(
    val isLoading: Boolean = false,
    val screens: List<Screen> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
    val recordingArea : WindowPlacement = WindowPlacement.Default,
)
