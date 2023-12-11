package recor.audio.presentation.state

data class Audio(
    val name: String,
    val path: String,
    val duration: String,
    val size: String,
    val date: String,
    val thumbnail: String,
    val isSelected: Boolean = false
)
