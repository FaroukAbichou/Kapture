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
import record.domain.ConfigurationManager
import screen.domain.WindowBounds

@Composable
fun HomeContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Button(
            onClick = {
                state.selectedScreen?.let {
                    onEvent(
                        HomeEvent.Record(
                            config =
                            ConfigurationManager(
                                screenId = it.id,
                            )
                        )
                    )
                }
            }
        ) {
            Text("Record")
        }

        Button(
            onClick = {
                val bounds = WindowBounds(x1 = 100, y1 = 100, x2 = 1500, y2 = 1500)
                state.selectedScreen?.let {
                    onEvent(
                        HomeEvent.RecordSection(
                            config = ConfigurationManager(
                                screenId = state.selectedScreen.id,
                                windowBounds = bounds
                            ),
                            bounds = bounds
                        )
                    )
                }
            }
        ) {
            Text("Record Section")
        }
        Button(
            onClick = {
                state.selectedScreen?.let {
                    onEvent(
                        HomeEvent.StartRecording(
                            config = ConfigurationManager(
                                screenId = state.selectedScreen.id,
                            ),
                            bounds = null
                        )
                    )
                }
            }
        ) {
            Text("Start Recording")
        }
        Button(
            onClick = {
                state.selectedScreen?.let {
                    onEvent(HomeEvent.StopRecording)
                }
            }
        ) {
            Text("Stop Recording")
        }
        state.screens.forEach { screen ->
            Button(
                onClick = {
                    onEvent(
                        HomeEvent.SelectScreen(
                            screenId = screen.id
                        )
                    )
                }
            ) {
                Text("Screen ${screen.id}")
            }
        }

    }

    state.selectedScreen?.let {
        Text(
            text = "Selected screen: ${it.id}"
        )
    }
}