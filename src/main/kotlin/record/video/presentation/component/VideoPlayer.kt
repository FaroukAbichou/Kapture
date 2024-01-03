package record.video.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sun.javafx.application.PlatformImpl
import core.components.ComposeJFXPanel
import core.util.FilePaths
import javafx.embed.swing.JFXPanel
import record.video.presentation.component.player.VideoPlayer
import record.video.presentation.component.player.rememberVideoPlayerState

@Composable
fun VideoPlayer(composeWindow: ComposeWindow) {
    // This finishListener ensures that the JavaFX event loop doesn't terminate unexpectedly.
    val finishListener = object : PlatformImpl.FinishListener {
        override fun idle(implicitExit: Boolean) {}
        override fun exitCalled() {}
    }

    DisposableEffect(Unit) {
        PlatformImpl.addListener(finishListener)

        onDispose {
            PlatformImpl.removeListener(finishListener)
        }
    }
    val jfxPanel = remember { JFXPanel() }
    val videoPath = remember { FilePaths.VideosPath + "/ScreenRec.mp4" }

    val videoPlayerState = rememberVideoPlayerState()
    val isPlaying = videoPlayerState.isPlaying
    val timeMillis = videoPlayerState.timeMillis
    val lengthMillis = videoPlayerState.lengthMillis

    Box(
        modifier = Modifier
            .size(1280.dp, 720.dp)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ){
        ComposeJFXPanel(
            jfxPanel = jfxPanel,
            composeWindow = composeWindow,
            onCreate = {
                val videoPlayer = VideoPlayer(
                    jfxPanel = jfxPanel,
                    videoPath = videoPath
                )
                videoPlayer.play()
            },
        )
    }
//    Box(
//        modifier = Modifier
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .align(Alignment.BottomCenter)
//                .background(Color.Red)
//                .padding(32.dp),
//        ) {
//            Row(
//                modifier = Modifier.align(Alignment.CenterHorizontally),
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                Button(
//                    onClick = {
//
//                    }
//                ) {
//                    Text("Backward")
//                }
//                Button(
//                    onClick = {
//                        // show =  !show
//                    }) {
//
//                    Text(if(isPlaying) "Pause" else "Play")
//                }
//                Button(
//                    onClick = {
//
//                    }) {
//
//                    Text("Forward")
//                }
//            }
//
//            if(lengthMillis != -1L) {
//                Slider(
//                    value = timeMillis.toFloat(),
//                    onValueChange = {
//
//                    },
//                    modifier= Modifier.fillMaxWidth()
//                )
//            }
//        }
//
//    }
}

@Composable
fun KpVideoPlayer(
    videoPlayer: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        videoPlayer()

    }
}