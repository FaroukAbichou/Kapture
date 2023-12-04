package util

import WindowBounds
import java.io.BufferedReader
import java.io.InputStreamReader

object Window {

    fun getWindowBounds(
        windowId: String
    ): WindowBounds? {
        try {
            val command = arrayOf("/usr/bin/osascript", "-e", "tell application \"$windowId\" to get the bounds of the front window")
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

            val bounds = output.split(", ").map { it.toIntOrNull() ?: return null }
            if (bounds.size == 4) {
                return WindowBounds(bounds[0], bounds[1], bounds[2], bounds[3])
            }

            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}
