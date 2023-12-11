package recor.audio.domain

import recor.audio.domain.model.Audio


interface AudioRepository {

    fun getAudiosByPath(filePath: String): List<Audio>
}