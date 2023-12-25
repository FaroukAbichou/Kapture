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
import record.settings.domain.SettingsRepository
import record.settings.presentation.event.SettingsEvent
import record.settings.presentation.state.SettingsState

class SettingsViewModel : KoinComponent {

    private val settingsRepository: SettingsRepository by inject()
    private val probRepository: ProbRepository by inject()

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    val coroutineScope = CoroutineScope(Dispatchers.IO)

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
        getScreens()
//        getAudioSources()
//        getCameras()

    }

    private fun getScreens() {
        _state.value = _state.value.copy(
            isLoading = true
        )
        coroutineScope.launch {
            _state.value = _state.value.copy(
                screens = probRepository.getScreens(),
                audioSources = probRepository.getAudioSources(),
                cameras = probRepository.getCameras(),
                isLoading = false
            )
        }
    }

//    private fun getAudioSources() {
//        coroutineScope.launch {
//            _state.value = _state.value.copy(
//                audioSources = probRepository.getAudioSources(),
//            )
//        }
//    }

//    private fun getCameras() {
//        coroutineScope.launch {
//            _state.value = _state.value.copy(
//                cameras = probRepository.getCameras(),
//            )
//        }
//    }
}