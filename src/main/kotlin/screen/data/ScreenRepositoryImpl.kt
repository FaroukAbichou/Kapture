package screen.data

import screen.domain.Screen
import screen.domain.ScreenRepository
import screen.domain.WindowBounds
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class ScreenRepositoryImpl : ScreenRepository {

    override fun getScreens(): List<Screen> {
        val command = listOf("/usr/sbin/system_profiler", "SPDisplaysDataType")
        val process = ProcessBuilder(command).start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readText()
        reader.close()

        var screenNumber = -1

        val resolutionRegex = "Resolution: (\\d+) x (\\d+)".toRegex()
        val screens = resolutionRegex.findAll(output).map { matchResult ->
            screenNumber++
            Screen(
                id =   screenNumber.toString(),
                name = matchResult.groupValues[2],
                bounds = WindowBounds(
                    x1 = 0,
                    y1 = 0,
                    x2 = matchResult.groupValues[1].toInt(),
                    y2 = matchResult.groupValues[2].toInt()
                )
            )
        }.toList()
        requestScreenRecordingPermission()
        return screens
    }

    fun getSupportedPixelFormats(): List<String> {
        val command = listOf("/usr/sbin/system_profiler", "SPDisplaysDataType")
        val process = ProcessBuilder(command).start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readText()
        reader.close()

        val pixelFormatRegex = "Pixel Format: (\\w+)".toRegex()
        val pixelFormats = pixelFormatRegex.findAll(output).map { matchResult ->
            matchResult.groupValues[1]
        }.toList()
        return pixelFormats
    }

    fun requestScreenRecordingPermission() {
        try {
            val script = """tell application "System Preferences"
reveal anchor "Privacy_ScreenCapture" of pane id "com.apple.preference.security"
activate
end tell"""
            val command = arrayOf("osascript", "-e", script)
            val processBuilder = ProcessBuilder(*command)
            processBuilder.start()
        } catch (e: IOException) {
            e.printStackTrace()
            // Handle exceptions
        }
    }

}