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
        player.setOnError {
            println("Media error occurred: ${player.error}")
        }
    }

    val playerState: VideoPlayerState
        get() = VideoPlayerState(
            isPlaying = player.status == MediaPlayer.Status.PLAYING,
            rate = player.rate,
            timeMillis = player.currentTime.toMillis(),
            lengthMillis = player.totalDuration.toMillis(),
            isMuted = player.isMute,
            volume = player.volume,
            isFullScreen = false,
            isLooping = player.cycleCount == MediaPlayer.INDEFINITE,
            isAutoPlay = player.isAutoPlay,
            isSeeking = false,
            isShowingControls = false,
    )

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

    fun setVolume(volume: Double) {
        player.volume = volume
    }
    fun dispose() {
        player.dispose()
    }
}
