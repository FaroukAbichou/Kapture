package probe.screen.data

import probe.screen.domain.ScreenRepository
import probe.screen.domain.model.Screen
import java.io.BufferedReader
import java.io.InputStreamReader


class ScreenRepositoryImpl : ScreenRepository {

    private fun getOperatingSystem(): String = System.getProperty("os.name").toLowerCase()

    override fun getScreens(): List<Screen> {
        val command = listOf("ffmpeg", "-f", "avfoundation", "-list_devices", "true", "-i", "")
        val process = ProcessBuilder(command).start()
        process.waitFor()

        val reader = BufferedReader(InputStreamReader(process.errorStream))
        val output = reader.readText()
        reader.close()

        val screenRegex = """\[(\d+)\] Capture screen (\d+)""".toRegex()

        return screenRegex.findAll(output).map {matchResult ->
            val screenNumber = matchResult.groupValues[2].toInt() + 1 //Todo fix this
            Screen(
                id = screenNumber.toString(),
                name = "Capture screen $screenNumber",
                height = getScreenResolution(screenNumber.toString())?.first ?: 100,
                width = getScreenResolution(screenNumber.toString())?.second ?: 100
            )
        }.toList()
    }

    override fun createDirectoriesIfNotExist() {

    }

    private fun getScreenResolution(screenId :String): Pair<Int, Int>? {
        return when (getOperatingSystem()) {
            "mac os x" -> getMacOSScreenResolution(screenId)
            "win" -> getWindowsScreenResolution(screenId)
            "linux" -> getLinuxScreenResolution(screenId)
            else -> null
        }
    }

    private fun getMacOSScreenResolution(screenId: String): Pair<Int, Int>? {
        val process = ProcessBuilder("system_profiler", "SPDisplaysDataType").start()
        process.waitFor()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val displayData = reader.readText()
        reader.close()

        val screenRegex = Regex("Resolution: (\\d+) x (\\d+)")
        val matchResults = screenRegex.findAll(displayData).toList()

        // Debugging: Print the number of matches found
        println("Number of matches found: ${matchResults.size}")

        println(screenId)
        println(matchResults.size)

        val index = screenId.toIntOrNull() ?: return null.also { println("Invalid screenId: $screenId") }
        if (index >= matchResults.size) return null.also { println("screenId out of range: $screenId") }

        matchResults.forEach { match ->
            println("Found resolution: ${match.groupValues[1]} x ${match.groupValues[2]}")
        }

        val (width, height) = matchResults[index].destructured
        return Pair(width.toInt(), height.toInt())
    }

    private fun getWindowsScreenResolution(screenId: String): Pair<Int, Int>? {
        val powershellCommand = """
        Add-Type -AssemblyName System.Windows.Forms
        [System.Windows.Forms.Screen]::AllScreens[$screenId].Bounds | 
        Format-Table -Property Width, Height -HideTableHeaders
    """.trimIndent()

        val process = ProcessBuilder("powershell.exe", "-Command", powershellCommand).start()
        process.waitFor()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readText().trim().split("\\s+".toRegex())
        reader.close()

        return if (output.size >= 2) Pair(output[0].toInt(), output[1].toInt()) else null
    }

    private fun getLinuxScreenResolution(screenId: String): Pair<Int, Int>? {
        val process = ProcessBuilder("xrandr", "--query").start()
        process.waitFor()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val resolutionRegex = Regex("""$screenId connected primary (\d+)x(\d+)""")
        val resolutionLine = reader.lineSequence().find { it.contains(screenId) }
        reader.close()

        return resolutionRegex.find(resolutionLine ?: "")?.destructured?.let { (width, height) ->
            Pair(width.toInt(), height.toInt())
        }
    }
}