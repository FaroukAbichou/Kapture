package screen.data

import screen.domain.Screen
import screen.domain.ScreenRepository
import screen.domain.WindowBounds
import java.io.BufferedReader
import java.io.InputStreamReader

class ScreenRepositoryImpl : ScreenRepository {
    override fun getScreenList(): List<Screen> {
        val command = listOf("/usr/sbin/system_profiler", "SPDisplaysDataType")
        val process = ProcessBuilder(command).start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readText()
        reader.close()

        val resolutionRegex = "Resolution: (\\d+) x (\\d+)".toRegex()
        return resolutionRegex.findAll(output).map { matchResult ->
            Screen(
                id = matchResult.groupValues[1],
                name = matchResult.groupValues[2],
                bounds = WindowBounds(
                    x1 = 0,
                    y1 = 0,
                    x2 = matchResult.groupValues[1].toInt(),
                    y2 = matchResult.groupValues[2].toInt()
                )
            )
        }.toList()
    }

    override fun getScreen(screenId: String): Screen {
        val command = listOf("/usr/sbin/system_profiler", "SPDisplaysDataType")
        val process = ProcessBuilder(command).start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = reader.readText()
        reader.close()

        val resolutionRegex = "Resolution: (\\d+) x (\\d+)".toRegex()
        return resolutionRegex.findAll(output).map { matchResult ->
            Screen(
                id = matchResult.groupValues[1],
                name = matchResult.groupValues[2],
                bounds = WindowBounds(
                    x1 = 0,
                    y1 = 0,
                    x2 = matchResult.groupValues[1].toInt(),
                    y2 = matchResult.groupValues[2].toInt()
                )
            )
        }.toList().first { it.id == screenId }
    }
}