package record.settings.presentation.event

import probe.domain.model.Device

sealed class SettingsEvent {
    data object NavigateToHome : SettingsEvent()
    data class SelectOutputLocation(val outputLocation: String) : SettingsEvent()
    data class SelectDevice(val device: Device) : SettingsEvent()

}