package record.video.presentation.component.player

import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaView
import java.io.File

class VideoPlayer (
    private val jfxPanel: JFXPanel,
    videoPath: String
) {
    private val media: Media = Media(File(videoPath).toURI().toString())
    private val mediaPlayer: MediaPlayer = MediaPlayer(media)
    private val root: Group = Group()
    private val mediaView: MediaView = MediaView(mediaPlayer)

    init {
        Platform.runLater {
            root.children.add(mediaView)
            jfxPanel.scene = Scene(root)
            mediaPlayer.isAutoPlay = true
        }
    }

    val isPlaying: Boolean get() = mediaPlayer.status == MediaPlayer.Status.PLAYING
    fun play(){
        if (Platform.isFxApplicationThread()) {
            mediaPlayer.play()
        } else {
            Platform.runLater { mediaPlayer.play() }
        }
    }
    fun setRate(rate: Float){
        if (Platform.isFxApplicationThread()) {
            mediaPlayer.rate = rate.toDouble()
        } else {
            Platform.runLater { mediaPlayer.rate = rate.toDouble() }
        }
    }
    fun setTime(millis: Long){
//        if (Platform.isFxApplicationThread()) {
//            mediaPlayer.seek(millis.toDuration())
//        } else {
//            Platform.runLater { mediaPlayer.seek(millis.toDouble()) }
//        }
    }
    fun setTimeAccurate(millis: Long){

    }

    fun getTimeMillis(): Long {
        return if (Platform.isFxApplicationThread()) {
            mediaPlayer.currentTime.toMillis().toLong()
        } else {
            var timeMillis = 0L
            Platform.runLater { timeMillis = mediaPlayer.currentTime.toMillis().toLong() }
            timeMillis
        }
    }
    fun getLengthMillis(): Long {
        return if (Platform.isFxApplicationThread()) {
            mediaPlayer.totalDuration.toMillis().toLong()
        } else {
            var lengthMillis = 0L
            Platform.runLater { lengthMillis = mediaPlayer.totalDuration.toMillis().toLong() }
            lengthMillis
        }
    }
    fun addOnTimeChangedListener( ) {

    }

    fun pause() {
        if (Platform.isFxApplicationThread()) {
            mediaPlayer.pause()
        } else {
            Platform.runLater { mediaPlayer.pause() }
        }
    }

    fun stop() {
        if (Platform.isFxApplicationThread()) {
            mediaPlayer.stop()
        } else {
            Platform.runLater { mediaPlayer.stop() }
        }
    }

    fun reload() {
        if (Platform.isFxApplicationThread()) {
            mediaPlayer.stop()
            mediaPlayer.seek(mediaPlayer.startTime)
            mediaPlayer.play()
        } else {
            Platform.runLater {
                mediaPlayer.stop()
                mediaPlayer.seek(mediaPlayer.startTime)
                mediaPlayer.play()
            }
        }
    }

    fun dispose() {
        if (Platform.isFxApplicationThread()) {
            mediaPlayer.dispose()
        } else {
            Platform.runLater { mediaPlayer.dispose() }
        }
    }
}