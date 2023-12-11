package recor.audio.data

import recor.audio.domain.model.Audio


interface AudioRepository {

    fun getAudiosByPath(filePath: String): List<Audio>
}