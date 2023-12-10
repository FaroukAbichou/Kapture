package record.presentation.state

import screen.domain.Screen
import screen.domain.WindowPlacement

data class RecordState(
    val isLoading: Boolean = false,
    val records: List<Record> = emptyList(),
    val error: Throwable? = null,
    val screens: List<Screen> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
    val recordingArea : WindowPlacement = WindowPlacement.Default,
)

