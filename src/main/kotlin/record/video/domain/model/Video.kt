package record.video.domain.model

data class Video(
    val name: String,
    val path: String,
    val duration: String,
    val size: Long,
    val date: String,
    val thumbnail: String,
    val isSelected: Boolean = false
)