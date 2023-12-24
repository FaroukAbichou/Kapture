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

        val audioSourceRegex = """\[AVFoundation input device @ 0x[a-f0-9]+\] \[1\] "(.+)" """.toRegex()
        return audioSourceRegex.findAll(output).map { matchResult ->
            AudioSource(
                id= UUID.randomUUID().toString(),
                name = matchResult.groupValues[1]
            )
        }.toList()
    }


    override fun getCameras(): List<Camera> {
        val command = listOf("ffmpeg", "-f", "avfoundation", "-list_devices", "true", "-i", "")
        val process = ProcessBuilder(command).start()
        process.waitFor()

        val reader = BufferedReader(InputStreamReader(process.errorStream))
        val output = reader.readText()
        reader.close()

        // Updated regex pattern to match the camera names
        val cameraRegex = """\[(\d+)\] (.+)""".toRegex()

        return cameraRegex.findAll(output).mapNotNull { matchResult ->
            // Check if the captured group is a camera (and not a screen or audio source)
            val deviceIndex = matchResult.groupValues[1]
            val deviceName = matchResult.groupValues[2]

            // Assuming camera indexes are less than a certain number (e.g., less than 10)
            // Adjust this based on how your system differentiates between cameras and screens
            if ((deviceIndex.toIntOrNull() ?: -1) < 10) {
                Camera(
                    id = UUID.randomUUID().toString(),
                    name = deviceName
                )
            } else null
        }.toList()
    }

}