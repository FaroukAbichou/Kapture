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
    val outputLocation : String = FilePaths.KapturePath,

    val selectedDevicesState : SelectedDevicesState = SelectedDevicesState()
){
    data class SelectedDevicesState(
        val selectedScreen: Screen? = null,
        val selectedCamera: Camera? = null,
        val selectedAudioSource: AudioSource? = null,
    )
}