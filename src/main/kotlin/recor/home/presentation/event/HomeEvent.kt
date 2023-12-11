package recor.home.presentation.event

import recor.record.domain.ConfigurationManager

sealed class HomeEvent {
    data class GetScreens(val config: ConfigurationManager) : HomeEvent()

}