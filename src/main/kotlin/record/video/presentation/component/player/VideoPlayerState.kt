package record.video.presentation.component.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class VideoPlayerState(
    val isPlaying: Boolean = false,
    val rate: Double = 1.0,
    val timeMillis: Double = 0.0,
    val lengthMillis: Double = 0.0,
    val isMuted: Boolean = false,
    val volume: Double = 1.0,
    val isFullScreen: Boolean = false,
    val isLooping: Boolean = false,
    val isAutoPlay: Boolean = false,
    val isSeeking: Boolean = false,
    val isShowingControls: Boolean = false
)
@Composable
fun rememberVideoPlayerState() = remember { VideoPlayerState() }