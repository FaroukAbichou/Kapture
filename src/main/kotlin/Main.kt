
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.window.*
import core.di.initKoin
import core.util.FilePaths
import javafx.embed.swing.JFXPanel
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javax.swing.SwingUtilities

fun main() {
    initKoin().koin
    application {
        val windowState = rememberWindowState(
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )
//        Window(
//            onCloseRequest = ::exitApplication,
//            state = windowState,
//            title = "Kapture"
//        ) {
//            KaptureTheme {
//                KpMainContent()
//            }
//        }

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Kapture"
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                SwingPanel(
                    factory = {
                        JFXPanel().apply {
                            SwingUtilities.invokeLater {
                                val media = Media("${FilePaths.VideosPath}/ScreenRec.mp4")
                                val mediaPlayer = MediaPlayer(media)
                                val mediaView = MediaView(mediaPlayer)

                                scene = Scene(Group(mediaView))
                                mediaPlayer.play()
                            }
                        }
                    }
                )
            }
        }
    }
}