package record.video.domain.model

import androidx.compose.ui.graphics.ImageBitmap
import core.MediaItem

data class Video(
    override val name: String,
    override val path: String,
    override val size: Long,
    override val dateCreated: String,
    override val thumbnail: ImageBitmap,
    val duration: Double,
    val isSelected: Boolean = false
): MediaItem {
    override fun play() {

    }

    override fun stop() {
        TODO()
    }

    override fun getDescription(): String {
        return "path='$path', size=$size, duration=$duration, dateCreated='$dateCreated'"
    }
}