package record.audio.data

import androidx.compose.ui.graphics.ImageBitmap
import core.util.FileHelper.AudioExtensions
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import probe.screen.domain.model.Screen
import record.audio.domain.AudioRepository
import record.audio.domain.model.Audio
import record.video.domain.model.RecordSettings
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Path

class AudioRepositoryImpl : AudioRepository {
    override fun getAudioByPath(filePath: String): Result<List<Audio>> {
        val audios = getFilesWithExtension(filePath, AudioExtensions)
        val result = audios.map { path ->
            Audio(
                name = path.fileName.toString(),
                path = path.toString(),
                size = getFileSize(path),
                dateCreated = getFileDate(path),
                duration = getAudioDuration(path),
                thumbnail = getDefaultAudioThumbnail()
            )
        }
        return try {
            Result.success(result)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    private fun getAudioDuration(path: Path): Double {
        try {
            val processBuilder = ProcessBuilder(
                "ffprobe",
                "-v", "error",
                "-show_entries", "format=duration",
                "-of", "default=noprint_wrappers=1:nokey=1",
                path.toString()
            )
            processBuilder.redirectErrorStream(true)
            val process = processBuilder.start()

            BufferedReader(InputStreamReader(process.inputStream)).use { reader ->
                val output = reader.readLine()
                process.waitFor()
                return output.toDoubleOrNull() ?: 0.0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0.0

    }

    private fun getDefaultAudioThumbnail(): ImageBitmap {
        return ImageBitmap(1 ,1)
    }

    override fun recordAudioWithTimeout(config: RecordSettings?) {
//        val command = arrayOf("/bin/sh", "-c", "ffmpeg -i \"${config.outputFile}\" -t ${config.durationInSeconds} \"${config.outputFile}\"")
//        val process = Runtime.getRuntime().exec(command)
//        process.waitFor()
    }

    override fun startAudioRecording(
        config: RecordSettings,
        selectedScreen: Screen,
    ) {

    }

    override fun stopAudioRecording() {

    }

}