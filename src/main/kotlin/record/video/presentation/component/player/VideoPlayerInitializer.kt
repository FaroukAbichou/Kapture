package record.video.presentation.component.player

import javafx.embed.swing.JFXPanel
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import java.io.File

class VideoPlayerInitializer (
    private val jfxPanel :  JFXPanel,
    private val videoPath : String
): Runnable {
    override fun run() {
        // Convert the file path to a URI string.
        val media = Media(File(videoPath).toURI().toString())
        val mediaPlayer = MediaPlayer(media)
        mediaPlayer.isAutoPlay = true
        val mediaView = MediaView(mediaPlayer)
        val root = Group()
        root.children.add(mediaView)
        val scene = Scene(root, 2000.0, 2000.0)
        jfxPanel.scene = scene
    }
}