package probe.screen.data

import core.util.FilePaths
import probe.screen.domain.ScreenRepository
import probe.screen.domain.model.Screen
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


class ScreenRepositoryImpl : ScreenRepository {

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
            println(screenNumber)
            println(getScreenResolution(screenNumber.toString()).first)
            println(getScreenResolution(screenNumber.toString()).second)
            Screen(
                id = screenNumber.toString(),
                name = "Capture screen $screenNumber",
                height =getScreenResolution(screenNumber.toString()).first ,
                width = getScreenResolution(screenNumber.toString()).second
            )
        }.toList()
    }
    fun getScreenResolution(screenId: String): Pair<Int, Int> {
        // Record a short clip to a temporary file
        val recordCommand = listOf("ffmpeg", "-f", "avfoundation", "-i", screenId, "-t", "1", "temp.mp4")
        ProcessBuilder(recordCommand).start().waitFor()

        // Extract metadata
        val probeCommand = listOf("ffprobe", "-v", "error", "-select_streams", "v:0", "-show_entries", "stream=width,height", "-of", "csv=p=0:s=x", "temp.mp4")
        val process = ProcessBuilder(probeCommand).start()
        process.waitFor()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val resolution = reader.readLine()
        println(resolution)
        reader.close()

        // Clean up the temporary file
        File("temp.mp4").delete()

        // Parse the resolution
        return resolution.split('x').let {
            Pair(it[0].toInt(), it[1].toInt())
        }
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

    override fun createDirectoriesIfNotExist() {
        val directories = listOf(FilePaths.VideosPath, FilePaths.AudiosPath, FilePaths.ImagesPath)
        directories.forEach { path ->
            val directory = File(path)
            if (!directory.exists()) {
                val created = directory.mkdirs()
                if (created) {
                    println("Directory created: $path")
                } else {
                    println("Failed to create directory: $path")
                }
            }
        }
    }
}