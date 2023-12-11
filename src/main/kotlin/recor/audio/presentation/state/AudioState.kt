package recor.audio.presentation.state

import recor.audio.domain.model.Audio

data class AudioState(
    val audios : List<Audio> = emptyList()
)
