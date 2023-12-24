package record.settings.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        getScreens()
        getAudioSources()
        getCameras()
        println(_state.value.screens)
        println(_state.value.cameras)
        println(_state.value.audioSources)
    }

    private fun getScreens() {
        _state.value = _state.value.copy(
            screens = probRepository.getScreens()
        )
    }

    private fun getAudioSources() {
        _state.value = _state.value.copy(
            audioSources = probRepository.getAudioSources()
        )
    }

    private fun getCameras() {
        _state.value = _state.value.copy(
            cameras = probRepository.getCameras()
        )
    }
}