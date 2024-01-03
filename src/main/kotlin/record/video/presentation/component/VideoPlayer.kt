package record.video.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.graphics.Color
import com.sun.javafx.application.PlatformImpl
import core.components.ComposeJFXPanel
import core.util.FilePaths
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import record.video.presentation.component.player.VideoPlayerInitializer

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
    // Remember a JFXPanel to embed the JavaFX content.
    val jfxPanel = remember { JFXPanel() }
    val videoPath = remember { FilePaths.VideosPath + "/ScreenRec.mp4" }

    // Define the composable that will hold the JFXPanel.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ComposeJFXPanel(
            jfxPanel = jfxPanel,
            composeWindow = composeWindow,
            onCreate = {
                Platform.runLater(
                    VideoPlayerInitializer(jfxPanel, videoPath)
                )

            }
        )
    }
}