import java.io.BufferedReader
import java.io.InputStreamReader

fun getTerminalWindowBounds(): List<Int>? {
    try {
        val command = arrayOf("/usr/bin/osascript", "-e", "tell application \"Terminal\" to get the bounds of the front window")
        val process = ProcessBuilder(*command).start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val errorReader = BufferedReader(InputStreamReader(process.errorStream))

        val output = reader.readText().trim()
        val error = errorReader.readText()

        reader.close()
        errorReader.close()

        process.waitFor()

        if (error.isNotEmpty()) {
            println("Error: $error")
            return null
        }

        return output.split(", ").map { it.toIntOrNull() ?: return null }
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }
}
