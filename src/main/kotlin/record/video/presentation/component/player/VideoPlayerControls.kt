package record.video.presentation.component.player

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Slider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forward10
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay10
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VideoPlayerControls(player: Player) {
    val videoPlayerState = rememberVideoPlayerState()
    val isPlaying = remember { mutableStateOf(videoPlayerState.isPlaying) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                isPlaying.value = !isPlaying.value
                if (isPlaying.value) {
                    player.play()
                } else {
                    player.pause()
                }
            }
        ) {
            Icon(
                imageVector = if (isPlaying.value) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying.value) "Pause" else "Play"
            )
        }

        // Skip Backward Button
        Button(
            onClick = {
                player.skip(-10)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Replay10,
                contentDescription = "Rewind 10 seconds"
            )
        }

        Button(
            onClick = {
                player.skip(10) // Implement a skip method in your Player class
            }
        ) {
            Icon(
                imageVector = Icons.Default.Forward10,
                contentDescription = "Forward 10 seconds"
            )
        }

        // Volume Slider
        Slider(
            value = videoPlayerState.volume.toFloat(),
            onValueChange = { newVolume ->
                player.setVolume(newVolume.toDouble())
            },
            valueRange = 0f..1f
        )
    }
}

@Composable
fun rememberMediaPlayer(mediaPath: String): Player {
    val player = remember { Player(mediaPath) }

    DisposableEffect(player) {
        onDispose {
            player.dispose() // Implement a dispose method in your Player class
        }
    }

    return player
}
