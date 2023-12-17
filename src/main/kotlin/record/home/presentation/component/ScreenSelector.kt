package record.home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import record.home.presentation.state.HomeState
import record.video.presentation.event.VideoEvent

@Composable
fun ScreenSelector(
    state: HomeState,
    onEvent: (VideoEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        repeat(state.screens.size) {
            Button(
                onClick = {
                    onEvent(
                        VideoEvent.SelectScreen(
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