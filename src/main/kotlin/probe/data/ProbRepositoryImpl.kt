package probe.data

import core.util.FilePaths
import probe.domain.ProbRepository
import probe.domain.model.AudioSource
import probe.domain.model.Camera
import probe.domain.model.Screen
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*


class ProbRepositoryImpl : ProbRepository {

    override fun getScreens(): List<Screen> {
        val command = listOf("ffmpeg", "-f", "avfoundation", "-list_devices", "true", "-i", "")
        val process = ProcessBuilder(command).start()
        process.waitFor()

        val reader = BufferedReader(InputStreamReader(process.errorStream))
        val output = reader.readText()
        reader.close()

        val screenRegex = """\[(\d+)\] Capture screen (\d+)""".toRegex()

        return screenRegex.findAll(output).map { matchResult ->
            val screenNumber = matchResult.groupValues[2]
            Screen(
                id = UUID.randomUUID().toString(),
                name = "Capture screen $screenNumber",
                height = 0,
                width = 0
            )
        }.toList()
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

    override fun getAudioSources(): List<AudioSource> {
        val command = listOf("ffmpeg", "-f", "avfoundation", "-list_devices", "true", "-i", "")
        val process = ProcessBuilder(command).start()
        process.waitFor()

        val reader = BufferedReader(InputStreamReader(process.errorStream))
        val output = reader.readText()
        reader.close()

        val audioSourceRegex = """\[(\d+)\] (.+)""".toRegex()

        return audioSourceRegex.findAll(output).mapNotNull { matchResult ->
            val deviceName = matchResult.groupValues[2]

            if (deviceName.contains("Microphone", ignoreCase = true) ||
                deviceName.contains("Audio", ignoreCase = true)) {
                AudioSource(
                    id = UUID.randomUUID().toString(),
                    name = deviceName
                )
            } else null
        }.toList()
    }


    override fun getCameras(): List<Camera> {
        val command = listOf("ffmpeg", "-f", "avfoundation", "-list_devices", "true", "-i", "")
        val process = ProcessBuilder(command).start()
        process.waitFor()

        val reader = BufferedReader(InputStreamReader(process.errorStream))
        val output = reader.readText()
        reader.close()

        val cameraRegex = """\[(\d+)\] (.+)""".toRegex()

        return cameraRegex.findAll(output).mapNotNull { matchResult ->
            val deviceName = matchResult.groupValues[2]

            if (deviceName.contains("Camera", ignoreCase = true) ||
                deviceName.contains("Webcam", ignoreCase = true)) {
                Camera(
                    id = UUID.randomUUID().toString(),
                    name = deviceName
                )
            } else null
        }.toList()
    }

}