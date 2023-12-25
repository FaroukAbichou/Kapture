package record.settings.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import probe.domain.ProbRepository
import probe.domain.model.AudioSource
import probe.domain.model.Camera
import probe.domain.model.Screen
import record.settings.domain.SettingsRepository
import record.settings.presentation.event.SettingsEvent
import record.settings.presentation.state.SettingsState

class SettingsViewModel : KoinComponent {

    private val settingsRepository: SettingsRepository by inject()
    private val probRepository: ProbRepository by inject()

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        getProb()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SelectOutputLocation -> changeOutputLocation(event.outputLocation)

            else -> {}
        }
    }

    private fun changeOutputLocation(outputLocation: String) {
        settingsRepository.changeOutputLocation(outputLocation)
    }

    private fun getProb() {
        _state.value = _state.value.copy(
            isLoading = true
        )
        val screens = probRepository.getScreens()
        val cameras = probRepository.getCameras()
        val audioSources = probRepository.getAudioSources()

        coroutineScope.launch {
            _state.value = _state.value.copy(
                screens = screens,
                cameras = cameras,
                audioSources = audioSources,
                isLoading = false
            )
        }
        selectDefaultDevices(
            screen = screens.first(),
            camera = cameras.first(),
            audioSource = audioSources.first()
        )
//        selectDefaultDevices()
//        getScreens()
//        getAudioSources()
//        getCameras()
    }

    private fun selectDefaultDevices(
        screen : Screen,
        camera: Camera,
        audioSource: AudioSource
    ){
        _state.value = _state.value.copy(
            selectedDevicesState = SettingsState.SelectedDevicesState(
                selectedScreen = screen,
                selectedCamera = camera,
                selectedAudioSource = audioSource,
            )
        )
    }
    private fun getScreens() {
        coroutineScope.launch {
            _state.value = _state.value.copy(
                screens = probRepository.getScreens()
            )
        }
    }

    private fun getAudioSources() {
        coroutineScope.launch {
            _state.value = _state.value.copy(
                audioSources = probRepository.getAudioSources(),
            )
        }
    }

    private fun getCameras() {
        coroutineScope.launch {
            _state.value = _state.value.copy(
                cameras = probRepository.getCameras(),
            )
        }
    }
}