//package record.video.presentation.component.player
//
//import javafx.scene.layout.BorderPane
//import javafx.scene.layout.Pane
//import javafx.scene.media.Media
//import javafx.scene.media.MediaPlayer
//import javafx.scene.media.MediaView
//import javafx.util.Duration
//
//class Player(file: String?) : BorderPane() {
//    private var media = Media(file)
//
//    private var player = MediaPlayer(media).apply {
//        isAutoPlay = false
//        setOnError {
//            println("Media error occurred: $error")
//        }
//    }
//
//    private var view: MediaView = MediaView(player).apply {
//        isPreserveRatio = true
//    }
//
//    private var mpane: Pane = Pane().apply {
//        children.add(view)
//        style = "-fx-background-color: black;"
//    }
//
//    init {
//        view.fitWidthProperty().bind(mpane.widthProperty())
//        view.fitHeightProperty().bind(mpane.heightProperty())
//
//        mpane.layoutBoundsProperty().addListener { _, _, _ ->
//            val width = mpane.width
//            val height = mpane.height
//
//            val viewWidth = view.prefWidth(-1.0)
//            val viewHeight = view.prefHeight(-1.0)
//
//            val x = (width - viewWidth) / 2
//            val y = (height - viewHeight) / 2
//
//            view.relocate(x, y)
//            view.resizeRelocate(x, y, viewWidth, viewHeight)
//        }
//
//        center = mpane
//    }
//
//    val timeMillis: Long
//        get() = player.currentTime.toMillis().toLong()
//
//    // Simplified player state
//    val playerState: VideoPlayerState
//        get() = VideoPlayerState(
//            isPlaying = player.status == MediaPlayer.Status.PLAYING,
//            rate = player.rate,
//            lengthMillis = player.totalDuration.toMillis(),
//            isMuted = player.isMute,
//            volume = player.volume,
//            isFullScreen = false,
//            isLooping = player.cycleCount == MediaPlayer.INDEFINITE,
//            isAutoPlay = player.isAutoPlay,
//            isSeeking = false,
//            isShowingControls = false
//        )
//
//    fun playOrPause() {
//        if (player.status == MediaPlayer.Status.PLAYING) {
//            player.pause()
//        } else {
//            player.play()
//        }
//    }
//
//    fun onTimeUpdate(callback: (Double) -> Unit) {
//        player.currentTimeProperty().addListener { _, _, newValue ->
//            callback(newValue.toMillis())
//        }
//    }
//
//
//    fun seek(time: Long) {
//        player.seek(Duration.millis(time.toDouble()))
//    }
//
//    fun skip(seconds: Int) {
//        player.seek(player.currentTime.add(Duration.seconds(seconds.toDouble())))
//    }
//
//    fun dispose() {
//        player.dispose()
//    }
//}
