package record.video.presentation.component.player

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
        modifier = Modifier.fillMaxWidth(.6f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Slider(
//            value = timeInMillis,
//            onValueChange = { timeInMillis = it },
//            valueRange = 0f..player.playerState.lengthMillis.toFloat()
//        )

        PlayerControlButtons(player)
    }
}