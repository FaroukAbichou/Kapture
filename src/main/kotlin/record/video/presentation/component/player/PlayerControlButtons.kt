//package record.video.presentation.component.player
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material.Button
//import androidx.compose.material.Icon
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Forward10
//import androidx.compose.material.icons.filled.Pause
//import androidx.compose.material.icons.filled.PlayArrow
//import androidx.compose.material.icons.filled.Replay10
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//
//@Composable
//fun PlayerControlButtons(player: Player) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        SkipButton(player, -10, Icons.Default.Replay10, "Rewind 10 seconds")
//
//        Button(
//            onClick = { player.playOrPause() }
//        ) {
//            Icon(
//                imageVector = if (player.playerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
//                contentDescription = if (player.playerState.isPlaying) "Pause" else "Play"
//            )
//        }
//
//        SkipButton(player, 10, Icons.Default.Forward10, "Forward 10 seconds")
//    }
//}
