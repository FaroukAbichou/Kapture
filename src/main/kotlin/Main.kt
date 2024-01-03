
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.sun.javafx.application.PlatformImpl
import core.di.initKoin
import core.util.FilePaths
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import java.awt.BorderLayout
import java.io.File
import javax.swing.JPanel

fun main() {
    initKoin().koin
    application(exitProcessOnExit = true) {
        VideoPlayerWindow()
    }
}

@Composable
fun VideoPlayerWindow() {
    val finishListener = object : PlatformImpl.FinishListener {
        override fun idle(implicitExit: Boolean) {}
        override fun exitCalled() {}
    }

    PlatformImpl.addListener(finishListener)

    Window(
        title = "Video Player",
        resizable = true,
        state = WindowState(
            placement = WindowPlacement.Floating,
            size = DpSize(800.dp, 600.dp)
        ),
        onCloseRequest = {
            PlatformImpl.removeListener(finishListener)
        },
        content = {
            val jfxPanel = remember { JFXPanel() }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                ComposeJFXPanel(
                    jfxPanel = jfxPanel,
                    composeWindow = window,
                    onCreate = {
                        Platform.runLater {
                            // Convert the file path to a URI string
                            val mediaPath = File(FilePaths.VideosPath + "/ScreenRec.mp4").toURI().toString()
                            val media = Media(mediaPath)
                            val mediaPlayer = MediaPlayer(media)
                            mediaPlayer.isAutoPlay = true
                            val mediaView = MediaView(mediaPlayer)
                            val root = Group()
                            root.children.add(mediaView)
                            val scene = Scene(root, 600.0, 400.0)
                            jfxPanel.scene = scene
                        }
                    }
                )
            }
        }
    )
}
@Composable
fun ComposeJFXPanel(
    composeWindow: ComposeWindow,
    jfxPanel: JFXPanel,
    onCreate: () -> Unit,
    onDestroy: () -> Unit = {}
) {
    val jPanel = remember { JPanel() }
    val density = LocalDensity.current.density

    Layout(
        content = {},
        modifier = Modifier.onGloballyPositioned { childCoordinates ->
            val coordinates = childCoordinates.parentCoordinates!!
            val location = coordinates.localToWindow(Offset.Zero).round()
            val size = coordinates.size
            jPanel.setBounds(
                (location.x / density).toInt(),
                (location.y / density).toInt(),
                (size.width / density).toInt(),
                (size.height / density).toInt()
            )
            jPanel.validate()
            jPanel.repaint()
        },
        measurePolicy = { _, _ -> layout(0, 0) {} })

    DisposableEffect(jPanel) {
        composeWindow.add(jPanel)
        jPanel.layout = BorderLayout(0, 0)
        jPanel.add(jfxPanel)
        onCreate()
        onDispose {
            onDestroy()
            composeWindow.remove(jPanel)
        }
    }
}
