package recor.audio.data

import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import recor.audio.domain.AudioRepository
import recor.audio.domain.model.Audio
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Path

class AudioRepositoryImpl : AudioRepository {
    override fun getAudiosByPath(filePath: String): List<Audio> {
        val audios = getFilesWithExtension(filePath, listOf(".mp3", ".wav"))

        return audios.map { path ->
            Audio(
                name = path.fileName.toString(),
                path = path.toString(),
                size = getFileSize(path),
                date = getFileDate(path),
                duration = getAudioDuration(path),
            )
        }
    }

    private fun getAudioDuration(path: Path): String {
        val command = arrayOf("/bin/sh", "-c", "ffmpeg -i \"${path.toAbsolutePath()}\" 2>&1 | grep Duration")
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))

        val durationLine = reader.readLine() ?: return "Unknown duration"

        return durationLine.substringAfter("Duration: ").substringBefore(",").trim()
    }
}