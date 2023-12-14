package record.audio.presentation.state

import record.audio.domain.model.Audio

data class AudioState(
    val audios: List<Audio> = emptyList(),
)
