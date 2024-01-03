package record.video.presentation.component.player

import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

class Player(file: String?) : BorderPane() {
    var media: Media
    var player: MediaPlayer
    var view: MediaView
    var mpane: Pane
    var bar: MediaBar

    init {
        media = Media(file)
        player = MediaPlayer(media)
        view = MediaView(player)
        mpane = Pane()
        mpane.children.add(view) // Calling the function getChildren

        // inorder to add the view
        center = mpane
        bar = MediaBar(player) // Passing the player to MediaBar
        bottom = bar // Setting the MediaBar at bottom
        style = "-fx-background-color:#bfc2c7" // Adding color to the mediabar
        player.play() // Making the video play
    }
}