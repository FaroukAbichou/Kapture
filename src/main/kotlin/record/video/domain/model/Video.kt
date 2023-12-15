package record.video.domain.model

import androidx.compose.ui.graphics.ImageBitmap
import core.MediaItem

data class Video(
    override val name: String,
    override val path: String,
    override val size: Long,
    override val duration: Double,
    override val dateCreated: String,
    override val thumbnail: ImageBitmap,
    val isSelected: Boolean = false
): MediaItem {
    override fun play() {

    }

    override fun stop() {
        TODO()
    }

    override fun getInfo(): String {
        TODO()
    }
}