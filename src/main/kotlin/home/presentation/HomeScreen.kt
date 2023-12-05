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
import record.data.RecorderRepositoryImpl
import record.domain.ConfigurationManager
import screen.domain.WindowBounds

@Composable
fun HomeScreen(
    recorderRepositoryImpl: RecorderRepositoryImpl
) {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    recorderRepositoryImpl.recordScreen(
                        config = ConfigurationManager(),
                        bounds = null
                    )
                }
            ) {
                Text("Record")
            }

            Button(
                onClick = {
                    val bounds = WindowBounds(x1 = 100, y1 = 100, x2 = 500, y2 = 400)
                    recorderRepositoryImpl.recordScreen(bounds = bounds, config = ConfigurationManager())
                }
            ) {
                Text("Record Section")
            }

            Button(
                onClick = {
                    recorderRepositoryImpl.recordScreenWithAudio(
                        config = ConfigurationManager(),
                        audioSource = "default"
                    )
                }
            ) {
                Text("Record with Audio")
            }

            Button(
                onClick = {
                    recorderRepositoryImpl.startRecording(
                        config = ConfigurationManager(),
                        bounds = null
                    )
                }
            ) {
                Text("Start Recording")
            }

            Button(
                onClick = {
                    recorderRepositoryImpl.stopRecording()
                }
            ) {
                Text("Stop Recording")
            }
        }
    }
}