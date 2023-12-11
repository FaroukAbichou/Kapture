package recor.audio.data

import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import recor.audio.domain.AudioRepository
import recor.audio.domain.model.Audio
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

    }

}