package home.presentation.event

import record.domain.ConfigurationManager

sealed class HomeEvent {
    data class GetScreens(val config: ConfigurationManager) : HomeEvent()

}