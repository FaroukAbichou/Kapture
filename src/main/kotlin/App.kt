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
import java.io.BufferedReader
import java.io.InputStreamReader

fun getTerminalWindowBounds(): List<Int>? {
    try {
        val process = Runtime.getRuntime().exec("osascript -e 'tell application \"Terminal\" to get the bounds of the front window'")
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val errorReader = BufferedReader(InputStreamReader(process.errorStream))

        val output = reader.readText()
        val error = errorReader.readText()

        reader.close()
        errorReader.close()

        process.waitFor()

        // Check for errors
        if (error.isNotEmpty()) {
            println("Error: $error")
            return null
        }

        // Parse the output
        return output.trim().split(", ").map { it.toIntOrNull() ?: return null }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}
@Composable
@Preview
fun App(screenRecorder: ScreenRecorder) {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = {
                println(getTerminalWindowBounds())
                screenRecorder.startRecording(
                    x = 910,
                    y = 105,
                    width = 925,
                    height = 641
                )
            }) {
                Text("Record")
            }
            Button(onClick = {
                screenRecorder.stopRecording()
            }) {
                Text("Stop")
            }
        }
    }
}
