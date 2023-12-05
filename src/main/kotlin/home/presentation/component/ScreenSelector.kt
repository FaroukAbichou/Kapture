package home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import home.presentation.event.HomeEvent
import home.presentation.state.HomeState

@Composable
fun ScreenSelector(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(state.screens.size) {
            Button(
                onClick = {
                    onEvent(
                        HomeEvent.SelectScreen(
                            screenId = state.screens[it].id
                        )
                    )
                }
            ) {
                Text("Screen $it")
            }
        }
    }
}