package record.settings.presentation.event

sealed class SettingsEvent {
    data object NavigateToHome : SettingsEvent()
    data class SelectOutputLocation(val outputLocation: String) : SettingsEvent()
}