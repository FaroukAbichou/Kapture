package record.home.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import record.home.presentation.component.HomeContent
import record.home.presentation.event.HomeEvent
import record.home.presentation.state.HomeState

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    Scaffold(
        topBar = {},
        bottomBar = {},
        containerColor = MaterialTheme.colorScheme.background,
    ) {

        HomeContent(
            state = state,
            onEvent = onEvent,
        )
    }
}