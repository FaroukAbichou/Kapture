package record.home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import record.home.presentation.event.HomeEvent
import record.home.presentation.state.HomeState

@Composable
fun HomeContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


    }

}
