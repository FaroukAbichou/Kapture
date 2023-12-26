package probe.audiosource.domain

import probe.audiosource.domain.model.AudioSource

interface AudioSourceRepository {
    fun getAudioSources() : List<AudioSource>
}