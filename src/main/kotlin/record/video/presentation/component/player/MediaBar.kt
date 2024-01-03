package record.video.presentation.component.player

import javafx.application.Platform
import javafx.beans.Observable
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.layout.HBox
import javafx.scene.layout.Priority
import javafx.scene.media.MediaPlayer
import javafx.scene.media.MediaPlayer.Status


class MediaBar(private val player: MediaPlayer) : HBox() {

    private val timeSlider = Slider().apply {
        HBox.setHgrow(this, Priority.ALWAYS)
        valueProperty().addListener { _: Observable ->
            if (isPressed) {
                player.seek(player.media.duration.multiply(value / 100))
            }
        }
    }

    private val volumeSlider = Slider().apply {
        prefWidth = 70.0
        minWidth = 30.0
        value = 100.0
        valueProperty().addListener { _: Observable ->
            if (isPressed) {
                player.volume = value / 100
            }
        }
    }

    private val playButton = Button("||").apply {
        prefWidth = 30.0
        setOnAction {
            when (player.status) {
                Status.PLAYING -> {
                    if (player.currentTime >= player.totalDuration) {
                        player.seek(player.startTime)
                        player.play()
                    } else {
                        player.pause()
                        text = ">"
                    }
                }
                Status.HALTED, Status.STOPPED, Status.PAUSED -> {
                    player.play()
                    text = "||"
                }
                else -> {
                }
            }
        }
    }

    private val volumeLabel = Label("Volume: ")

    init {
        alignment = Pos.CENTER
        padding = Insets(5.0, 10.0, 5.0, 10.0)
        children.addAll(playButton, timeSlider, volumeLabel, volumeSlider)
        player.currentTimeProperty().addListener { _: Observable -> updateValues() }
    }

    private fun updateValues() {
        Platform.runLater {
            timeSlider.value = player.currentTime.toMillis() / player.totalDuration.toMillis() * 100
        }
    }
}
