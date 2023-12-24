package record.settings.presentation.state

import core.util.FilePaths
import probe.domain.model.AudioSources
import probe.domain.model.Camera
import probe.domain.model.Screen

data class SettingsState(
    val screens: List<Screen> = emptyList(),
    val audioSources: List<AudioSources> = emptyList(),
    val cameras: List<Camera> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
    val outputLocation : String = FilePaths.KapturePath
)
