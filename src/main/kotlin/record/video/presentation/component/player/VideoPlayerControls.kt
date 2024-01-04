package record.video.presentation.component.player

import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun VideoPlayerControls(player: Player) {
    var timeInMillis by remember { mutableStateOf(0f) }
    val playerTime by remember { mutableStateOf(player.timeMillis.toFloat()) }

    LaunchedEffect(player) {
        player.onTimeUpdate { newTime ->
            timeInMillis = newTime.toFloat()
        }
    }

    LaunchedEffect(timeInMillis) {
        if (timeInMillis != playerTime) {
            player.seek(timeInMillis.toLong())
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = timeInMillis,
            onValueChange = { timeInMillis = it },
            valueRange = 0f..player.playerState.lengthMillis.toFloat()
        )

        PlayerControlButtons(player)
    }
}

@Composable
fun PlayerControlButtons(player: Player) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { player.playOrPause() }
        ) {
            Icon(
                imageVector = if (player.playerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (player.playerState.isPlaying) "Pause" else "Play"
            )
        }

        // Skip Backward Button
        SkipButton(player, -10, Icons.Default.Replay10, "Rewind 10 seconds")

        // Skip Forward Button
        SkipButton(player, 10, Icons.Default.Forward10, "Forward 10 seconds")
    }
}

@Composable
fun SkipButton(
    player: Player,
    skipSeconds: Int,
    icon: ImageVector,
    contentDesc: String
) {
    Button(
        onClick = { player.skip(skipSeconds) }
    ) {
        Icon(imageVector = icon, contentDescription = contentDesc)
    }
}