package record.video.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sun.javafx.application.PlatformImpl
import core.components.ComposeJFXPanel
import core.util.FilePaths
import javafx.embed.swing.JFXPanel
import javafx.scene.text.Text
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
                .size(400.dp, 400.dp)
                .background(Color.Green),
            contentAlignment = Alignment.Center
        ){
            ComposeJFXPanel(
                jfxPanel = remember { JFXPanel() },
                composeWindow = composeWindow,
                size = Size(200f, 200f),
                onCreate = {
                    val videoPlayer = VideoPlayer(
                        jfxPanel = jfxPanel,
                        videoPath = videoPath
                    )
                    videoPlayer.play()
                },
            )
        }

        Column(
            modifier = Modifier
                .size(800.dp, 800.dp)
                .background(Color.Red)
                .padding(32.dp),
        ) {
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {

                    }
                ) {
                    Text("Backward")
                }
                Button(
                    onClick = {
                        // show =  !show
                    }) {

                    Text(if(isPlaying) "Pause" else "Play")
                }
                Button(
                    onClick = {

                    }) {

                    Text("Forward")
                }
            }

            if(lengthMillis != -1L) {
                Slider(
                    value = timeMillis.toFloat(),
                    onValueChange = {

                    },
                    modifier= Modifier.fillMaxWidth()
                )
            }
        }

}