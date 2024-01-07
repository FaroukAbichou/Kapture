package probe.audiosource.data

import probe.audiosource.domain.AudioSourceRepository
import probe.audiosource.domain.model.AudioSource


class AudioSourceRepositoryImpl : AudioSourceRepository {

    override fun getAudioSources(): List<AudioSource> {
        return listOf(AudioSource("1", "Audio Source 1"))
    }

}