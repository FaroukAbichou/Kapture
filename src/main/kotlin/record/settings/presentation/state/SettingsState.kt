package record.settings.presentation.state

import probe.domain.model.Screen

data class SettingsState(
    val screens: List<Screen> = emptyList(),
    val selectedScreen: Screen = Screen.defaultScreen,
)
