package record.settings.presentation.event

sealed class SettingsEvent {
    data object NavigateToHome : SettingsEvent()
    data class GetScreens(val arg: String) : SettingsEvent()
}