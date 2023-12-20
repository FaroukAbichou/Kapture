package record.settings.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import record.image.domain.ImageRepository
import record.settings.presentation.event.SettingsEvent
import record.settings.presentation.state.SettingsState

class SettingsViewModel : KoinComponent {

    private val imageRepository: ImageRepository by inject()

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.GetScreens -> {

            }

            else -> {}
        }
    }
}