package record.video.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.sun.javafx.application.PlatformImpl
import core.components.ComposeJFXPanel
import core.components.helper.rememberMediaPlayer
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import record.video.presentation.component.player.VideoPlayerControls
import java.io.File


@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    contentScale: ContentScale,
    videoPath: String,
    composeWindow: ComposeWindow
) {
    val jfxPanel = JFXPanel()


    // This finishListener ensures that the JavaFX event loop doesn't terminate unexpectedly.
    val finishListener = object : PlatformImpl.FinishListener {
        override fun idle(implicitExit: Boolean) {}
        override fun exitCalled() {}
    }


    DisposableEffect(Unit) {
        PlatformImpl.addListener(finishListener)
        onDispose { PlatformImpl.removeListener(finishListener) }
    }


    val mediaPath = File(videoPath).toURI().toString()
    val player = rememberMediaPlayer(mediaPath)

    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(300.dp),
            contentAlignment = Alignment.Center,
        ) {
            ComposeJFXPanel(
                modifier = Modifier.matchParentSize(),
                jfxPanel = jfxPanel,
                composeWindow = composeWindow,
                onCreate = { jfxPanel.scene = Scene(player) },
                onDestroy = {}
            )
        }

        VideoPlayerControls(player = player)
    }
}