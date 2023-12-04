
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun App(screenRecorder: ScreenRecorder) {
    val bonds = WindowBounds(400, 1000, 100, 100)
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                    screenRecorder.recordScreen("output.mp4", 5)
                }
            ) {
                Text("Record")
            }
            Button(
                onClick = {
                    screenRecorder.recordScreenSection("output.mp4", 5, bonds)
                }
            ) {
                Text("Record Section")
            }
        }
    }
}
