package record.settings.presentation.state

import core.util.FilePaths
import probe.domain.model.Screen

data class SettingsState(
    val screens: List<Screen> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
    val outputLocation : String = FilePaths.KapturePath
)
