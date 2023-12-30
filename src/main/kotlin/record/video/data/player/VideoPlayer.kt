
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javax.swing.SwingUtilities

class VideoPlayer {

    private var mediaPlayer: MediaPlayer? = null

    fun playVideo(videoPath: String) {
        SwingUtilities.invokeLater {
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
            }
            val media = Media(videoPath)
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
