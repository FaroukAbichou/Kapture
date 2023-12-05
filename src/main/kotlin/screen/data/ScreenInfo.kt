package screen.data

import screen.domain.Resolution
import java.io.BufferedReader
import java.io.InputStreamReader

class ScreenInfo {
    fun getScreenResolutions(): List<Resolution> {
        val command = listOf("/usr/sbin/system_profiler", "SPDisplaysDataType")
        val process = ProcessBuilder(command).start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readText()
        reader.close()

        val resolutionRegex = "Resolution: (\\d+) x (\\d+)".toRegex()
        return resolutionRegex.findAll(output).map { matchResult ->
            Resolution(matchResult.groupValues[1].toInt(), matchResult.groupValues[2].toInt())
        }.toList()
    }

    fun getNumberOfScreens(): Int {
        val command = listOf("/usr/sbin/system_profiler", "SPDisplaysDataType")
        val process = ProcessBuilder(command).start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readText()
        reader.close()

        val resolutionRegex = "Resolution: (\\d+) x (\\d+)".toRegex()
        return resolutionRegex.findAll(output).count()
    }
}