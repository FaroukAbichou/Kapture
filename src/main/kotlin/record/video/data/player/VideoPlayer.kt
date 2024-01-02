package record.video.data.player

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import java.io.File
import javax.swing.SwingUtilities

class VideoPlayer {

    init {
        // Ensure JavaFX is initialized
//        JFXPanel()
    }

    private var mediaPlayer: MediaPlayer? = null

    fun playVideo(videoPath: String) {
        SwingUtilities.invokeLater {
            mediaPlayer?.stop()

            // Convert the file path to a URI
            val mediaFile = File(videoPath)
            val mediaUri = mediaFile.toURI().toString()

            val media = Media(mediaUri)
            mediaPlayer = MediaPlayer(media).apply {
                play()
            }
        }
    }

    fun stopVideo() {
        SwingUtilities.invokeLater {
            mediaPlayer?.stop()
        }
    }
}
