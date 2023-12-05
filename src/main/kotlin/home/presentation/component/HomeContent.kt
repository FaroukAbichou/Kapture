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
                    println("selected Screnn is ${it}")
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
                val bounds = WindowBounds(x1 = 100, y1 = 100, x2 = 500, y2 = 400)
                state.selectedScreen?.let {
                    println("selected Screnn is ${it}")
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
        state.screens.forEach { screen ->
            Button(
                onClick = {
                    println("selected Screnn is ${screen.id}")

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
            text = "Selected screen: ${it}"
        )
    }
}