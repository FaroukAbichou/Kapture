package recor.audio.domain.model

data class Audio(
    val name: String,
    val path: String,
    val duration: String,
    val size: Long,
    val date: String,
    val isSelected: Boolean = false
)
