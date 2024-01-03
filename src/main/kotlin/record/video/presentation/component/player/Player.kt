package record.video.presentation.component.player

import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView

class Player(file: String?) : BorderPane() {
    var media = Media(file)
    var player = MediaPlayer(media)
    var view = MediaView(player)
    var mpane = Pane()

    val isPlaying: Boolean
        get() = player.status == MediaPlayer.Status.PLAYING
    init {
        player.isAutoPlay = false
        mpane.children.add(view)
        center = mpane
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

    fun setRate(rate: Double) {
        player.rate = rate
    }
}