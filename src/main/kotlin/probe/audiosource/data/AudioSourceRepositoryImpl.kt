package probe.audiosource.data

import probe.audiosource.domain.AudioSourceRepository
import probe.audiosource.domain.model.AudioSource
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*


class AudioSourceRepositoryImpl : AudioSourceRepository {

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

}