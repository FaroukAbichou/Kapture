package recor.home.presentation.event

import recor.record.domain.model.ConfigurationManager

sealed class HomeEvent {
    data class GetScreens(val config: ConfigurationManager) : HomeEvent()

}