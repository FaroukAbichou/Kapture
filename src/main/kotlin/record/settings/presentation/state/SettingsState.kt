package record.settings.presentation.state

import core.util.FilePaths
import probe.domain.model.AudioSource
import probe.domain.model.Camera
import probe.domain.model.Screen

data class SettingsState(
    val isLoading :Boolean = false,
    val screens: List<Screen> = emptyList(),
    val audioSources: List<AudioSource> = emptyList(),
    val cameras: List<Camera> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
    val outputLocation : String = FilePaths.KapturePath
)