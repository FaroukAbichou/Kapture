package record.video.presentation.component.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class VideoPlayerState (
    val isPlaying: Boolean = false,
    val rate: Float = 1f,
    val timeMillis: Long = 0L,
    val lengthMillis: Long = 0L,
    val isMuted: Boolean = false,
    val volume: Float = 1f,
    val isFullScreen: Boolean = false,
    val isLooping: Boolean = false,
    val isAutoPlay: Boolean = false,
    val isSeeking: Boolean = false,
    val isShowingControls: Boolean = false,
)
@Composable
fun rememberVideoPlayerState() = remember { VideoPlayerState() }