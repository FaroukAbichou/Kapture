package screen.data

import screen.domain.Screen
import screen.domain.ScreenRepository
import java.io.BufferedReader
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
                width = matchResult.groupValues[1].toInt(),
                height = matchResult.groupValues[2].toInt()
            )
        }.toList()
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


}