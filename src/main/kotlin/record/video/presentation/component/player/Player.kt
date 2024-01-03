package record.video.presentation.component.player

import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import javafx.util.Duration

class Player(file: String?) : BorderPane() {
    private var media = Media(file)
    var player = MediaPlayer(media)
    private var view = MediaView(player)
    private var mpane = Pane()

    init {
        player.isAutoPlay = false
        mpane.children.add(view)
        center = mpane

        // Handle media errors
//        player.onError = Runnable {
//            println("Error occurred in MediaPlayer: ${player.error}")
//        }
    }

    val isPlaying: Boolean
        get() = player.status == MediaPlayer.Status.PLAYING

    val currentTime: Double
        get() = player.currentTime.toMillis()

    val totalDuration: Double
        get() = media.duration.toMillis()

    var volume: Double
        get() = player.volume
        set(value) {
            player.volume = value.coerceIn(0.0, 1.0) // Ensure volume is within [0, 1]
        }

    fun play() {
        player.play()
    }

    fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
    }

    fun skip(seconds: Int) {
        player.seek(player.currentTime.add(Duration.seconds(seconds.toDouble())))
    }

    fun setRate(rate: Double) {
        player.rate = rate
    }


    fun dispose() {
        player.dispose()
    }
}
