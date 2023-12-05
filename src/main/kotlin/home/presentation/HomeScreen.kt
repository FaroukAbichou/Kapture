package home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import home.presentation.event.HomeEvent
import home.presentation.state.HomeState
import record.domain.ConfigurationManager
import screen.domain.WindowBounds

@Composable
fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit = {},
) {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    onEvent(
                        HomeEvent.Record(
                            config = ConfigurationManager(),
                        )
                    )
                }
            ) {
                Text("Record")
            }

            Button(
                onClick = {
                    val bounds = WindowBounds(x1 = 100, y1 = 100, x2 = 500, y2 = 400)
                    onEvent(
                        HomeEvent.RecordSection(
                            config = ConfigurationManager(),
                            bounds = bounds
                        )
                    )
                }
            ) {
                Text("Record Section")
            }

            Button(
                onClick = {
                    onEvent(
                        HomeEvent.RecordWithAudio(
                            config = ConfigurationManager(),
                            audioSource = "default"
                        )
                    )
                }
            ) {
                Text("Record with Audio")
            }

            Button(
                onClick = {
                    onEvent(
                        HomeEvent.StartRecording(
                            config = ConfigurationManager(),
                            bounds = null

                        )
                    )
                }
            ) {
                Text("Start Recording")
            }

            Button(
                onClick = {
                    onEvent(HomeEvent.StopRecording)
                }
            ) {
                Text("Stop Recording")
            }
        }
    }
}