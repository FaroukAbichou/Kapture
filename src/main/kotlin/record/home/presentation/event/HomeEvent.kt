package record.home.presentation.event

import record.video.domain.model.RecordSettings

sealed class HomeEvent {
    data class GetScreens(val config: RecordSettings) : HomeEvent()
    data class NavigateToSettings(val arg: String) : HomeEvent()

}