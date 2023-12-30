
import javafx.application.Platform
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer

class VideoPlayer {

    private lateinit var mediaPlayer: MediaPlayer

    fun playVideo(videoPath: String) {
        if (!this::mediaPlayer.isInitialized) {
            Platform.startup {
                val media = Media(videoPath)
                mediaPlayer = MediaPlayer(media)
                mediaPlayer.play()
            }
        } else {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer(Media(videoPath))
            mediaPlayer.play()
        }
    }

}
