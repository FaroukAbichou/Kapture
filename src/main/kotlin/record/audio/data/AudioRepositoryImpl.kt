package record.audio.data

import androidx.compose.ui.graphics.ImageBitmap
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import probe.domain.model.Screen
import record.audio.domain.AudioRepository
import record.audio.domain.model.Audio
import record.video.domain.model.RecordSettings
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Path

class AudioRepositoryImpl : AudioRepository {
    override fun getAudioByPath(filePath: String): List<Audio> {
        val audios = getFilesWithExtension(filePath, listOf(".mp3", ".wav"))

        return audios.map { path ->
            Audio(
                name = path.fileName.toString(),
                path = path.toString(),
                size = getFileSize(path),
                dateCreated = getFileDate(path),
                duration = 0.0,
                thumbnail = ImageBitmap(1, 1)
            )
        }
    }

    override fun recordAudioWithTimeout(config: RecordSettings?) {
//        val command = arrayOf("/bin/sh", "-c", "ffmpeg -i \"${config.outputFile}\" -t ${config.durationInSeconds} \"${config.outputFile}\"")
//        val process = Runtime.getRuntime().exec(command)
//        process.waitFor()
    }

    override fun startAudioRecording(
        config: RecordSettings,
        selectedScreen: Screen
    ) {

    }

    override fun stopAudioRecording() {

    }

    private fun getAudioDuration(path: Path): String {
        val command = arrayOf("/bin/sh", "-c", "ffmpeg -i \"${path.toAbsolutePath()}\" 2>&1 | grep Duration")
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))

        val durationLine = reader.readLine() ?: return "Unknown duration"

        return durationLine.substringAfter("Duration: ").substringBefore(",").trim()
    }

}