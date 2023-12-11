package recor.home.presentation.event

import recor.video.domain.model.RecordSettings

sealed class HomeEvent {
    data class GetScreens(val config: RecordSettings) : HomeEvent()

}