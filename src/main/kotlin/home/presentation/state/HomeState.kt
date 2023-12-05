package home.presentation.state

import screen.domain.Screen

data class HomeState(
    val isLoading: Boolean = false,
    val screens : List<Screen> = emptyList(),
    val selectedScreenId: Int = 0,
)
