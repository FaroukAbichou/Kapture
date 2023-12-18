package record.audio.domain.model

import androidx.compose.ui.graphics.ImageBitmap
import core.MediaItem

data class Audio(
    override val name: String,
    override val path: String,
    override val size: Long,
    override val thumbnail: ImageBitmap,
    override val dateCreated: String,
    val duration: Double,
    val isSelected: Boolean = false,
) : MediaItem {
    override fun play() {
        TODO()
    }

    override fun stop() {
        TODO()
    }

    override fun getDescription(): String {
        return "path='$path', size=$size, duration=$duration, dateCreated='$dateCreated'"
    }
}
