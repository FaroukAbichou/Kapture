//package core.components.helper
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.remember
//import record.video.presentation.component.player.Player
//
//@Composable
//fun rememberMediaPlayer(file: String): Player {
//    val mediaPlayer = remember { Player(file) }
//
//    DisposableEffect(mediaPlayer) {
//        onDispose {
////            mediaPlayer.stop() // Stop the player to release resources
//            mediaPlayer.dispose() // Dispose the player to release resources
//        }
//    }
//
//    return mediaPlayer
//}