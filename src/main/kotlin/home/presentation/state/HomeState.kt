package home.presentation.state

import screen.domain.Resolution

data class HomeState(
    val isLoading: Boolean = false,
    val resolutions: List<Resolution> = emptyList(),
    val numberOfScreens: Int = 0,
    val selectedScreenId: Int = 1,
)
