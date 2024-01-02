package record.settings.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import probe.audiosource.domain.AudioSourceRepository
import probe.audiosource.domain.model.AudioSource
import probe.camera.domain.CameraRepository
import probe.camera.domain.model.Camera
import probe.core.Device
import probe.screen.domain.ScreenRepository
import probe.screen.domain.model.Screen
import record.settings.domain.SettingsRepository
import record.settings.presentation.event.SettingsEvent
import record.settings.presentation.state.SettingsState

class SettingsViewModel : KoinComponent {

    private val settingsRepository: SettingsRepository by inject()
    private val cameraRepository: CameraRepository by inject()
    private val screenRepository : ScreenRepository by inject()
    private val audioSourceRepository : AudioSourceRepository by inject()

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        getProb()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SelectOutputLocation ->
                changeOutputLocation(event.outputLocation)

            is SettingsEvent.SelectDevice -> selectDevice(event.device)

            else -> {}
        }
    }

    private fun changeOutputLocation(outputLocation: String) {
        settingsRepository.changeOutputLocation(outputLocation)
        _state.value = _state.value.copy(
            outputLocation = outputLocation
        )
    }

    private fun getProb() {
        _state.value = _state.value.copy(
            isLoading = true
        )
        coroutineScope.launch {
            val screens = screenRepository.getScreens()
            val cameras = cameraRepository.getCameras()
            val audioSources = audioSourceRepository.getAudioSources()
            _state.value = _state.value.copy(
                screens = screens,
                cameras = cameras,
                audioSources = audioSources,
                isLoading = false
            )
            selectDefaultDevices(
                screen = screens.first(),
                camera = cameras.firstOrNull(),
                audioSource = audioSources.first()
            )
        }
//        selectDefaultDevices()
//        getScreens()
//        getAudioSources()
//        getCameras()
    }

    private fun selectDevice(device: Device){
        when(device){
            is Screen -> {
                _state.value = _state.value.copy(
                    selectedDevicesState = SettingsState.SelectedDevicesState(
                        selectedScreen = device
                    )
                )
            }
            is Camera -> {
                _state.value = _state.value.copy(
                    selectedDevicesState = SettingsState.SelectedDevicesState(
                        selectedCamera = device
                    )
                )
            }
            is AudioSource -> {
                _state.value = _state.value.copy(
                    selectedDevicesState = SettingsState.SelectedDevicesState(
                        selectedAudioSource = device
                    )
                )
            }
        }
    }

    private fun getScreens() {
        coroutineScope.launch {
            _state.value = _state.value.copy(
                screens = screenRepository.getScreens()
            )
        }
    }

    private fun getAudioSources() {
        coroutineScope.launch {
            _state.value = _state.value.copy(
                audioSources = audioSourceRepository.getAudioSources(),
            )
        }
    }

    private fun getCameras() {
        coroutineScope.launch {
            _state.value = _state.value.copy(
                cameras = cameraRepository.getCameras(),
            )
        }
    }

    private fun selectDefaultDevices(
        screen : Screen,
        camera: Camera?,
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
}
