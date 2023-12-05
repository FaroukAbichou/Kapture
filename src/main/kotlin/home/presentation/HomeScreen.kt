package home.presentation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import home.presentation.component.HomeContent
import home.presentation.event.HomeEvent
import home.presentation.state.HomeState

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit = {},
) {
    if (state.isLoading) {
        Text("Loading...")
    } else {
        HomeContent(
            state = state,
            onEvent = onEvent
        )
    }
}