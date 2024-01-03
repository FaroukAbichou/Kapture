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

    fun play() {
        if (Platform.isFxApplicationThread()) {
            mediaPlayer.play()
        } else {
            Platform.runLater { mediaPlayer.play() }
        }
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