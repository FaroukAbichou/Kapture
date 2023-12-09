package home.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
            },
            enabled = state.selectedScreen != null
        ) {
            Text("Record")
        }

        Button(
            onClick = {
                val bounds = WindowBounds(x1 = 100, y1 = 100, x2 = 1000, y2 = 1000)
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
            },
            enabled = state.selectedScreen != null
        ) {
            Text("Record Section")
        }
        Button(
            onClick = {
                val bounds = WindowBounds(x1 = 100, y1 = 100, x2 = 1000, y2 = 1000)
                state.selectedScreen?.let {
                    onEvent(
                        HomeEvent.StartRecording(
                            config = ConfigurationManager(
                                screenId = state.selectedScreen.id,
                                windowBounds = bounds
                            ),
                            bounds = bounds
                        )
                    )
                }
            },
            enabled = state.selectedScreen != null
        ) {
            Text("Start Recording")
        }

        Button(
            onClick = {
                val bounds = WindowBounds(x1 = 100, y1 = 100, x2 = 1000, y2 = 1000)

                state.selectedScreen?.let {
                    onEvent(HomeEvent.PauseRecording)
                }
            },
            enabled = state.selectedScreen != null
        ) {
            Text("Pause Recording")
        }

        Button(
            onClick = {
                val bounds = WindowBounds(x1 = 100, y1 = 100, x2 = 1000, y2 = 1000)

                state.selectedScreen?.let {
                    onEvent(HomeEvent.DiscardRecording)
                }
            },
            enabled = state.selectedScreen != null
        ) {
            Text("Discard Recording")
        }

        Button(
            onClick = {
                val bounds = WindowBounds(x1 = 100, y1 = 100, x2 = 1000, y2 = 1000)

                state.selectedScreen?.let {
                    onEvent(HomeEvent.ResumeRecording(
                        config = ConfigurationManager(
                            screenId = state.selectedScreen.id,
                            windowBounds = bounds
                        ),
                        bounds = bounds
                    ))
                }
            },
            enabled = false
        ) {
            Text("Resume Recording")
        }
        Button(
            onClick = {

                state.selectedScreen?.let {
                    onEvent(HomeEvent.StopRecording)
                }
            },
            enabled = state.selectedScreen != null

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
                },
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