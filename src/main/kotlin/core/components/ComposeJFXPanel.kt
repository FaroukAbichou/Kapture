package core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.round
import core.util.FilePaths
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.media.Media
import javafx.stage.FileChooser
import record.video.presentation.component.player.Player
import java.awt.BorderLayout
import java.io.File
import javax.swing.JPanel

@Composable
fun ComposeJFXPanel(
    composeWindow: ComposeWindow,
    jfxPanel: JFXPanel,
    size: Size,
    onCreate: () -> Unit,
    onDestroy: () -> Unit = {}
) {
    val jPanel = remember { JPanel() }
    val density = LocalDensity.current.density

    val mediaPath = File(
        FilePaths.VideosPath + "/ScreenRec.mp4"
    ).toURI().toString()
    val media = Media(mediaPath)
    Box(
        modifier = Modifier
            .onGloballyPositioned { childCoordinates ->
                val coordinates = childCoordinates.parentCoordinates!!
                val location = coordinates.localToWindow(Offset.Zero).round()
                jPanel.setBounds(
                    (location.x / density).toInt(),
                    (location.y / density).toInt(),
                    (size.width / density).toInt(),
                    (size.height / density).toInt()
                )
                jPanel.validate()
                jPanel.repaint()
            },
        content = {}
    )

    DisposableEffect(jPanel) {
        composeWindow.add(jPanel)
        jPanel.layout = BorderLayout(0, 0)
        jPanel.add(jfxPanel)

        // Setup JavaFX Scene
        Platform.runLater {
            val menuBar = MenuBar()
            val menu = Menu("File")
            val openItem = MenuItem("Open").apply {
                setOnAction {
                    val fileChooser = FileChooser()
                    val file = fileChooser.showOpenDialog(null)
                    if (file != null) {
                        // Here, you create and play a new video based on the selected file
                        val newPlayer = Player(file.toURI().toURL().toExternalForm())
                        // Set any additional properties of the player here
                        // For example, newPlayer.setTop(menuBar)
                    }
                }
            }
            menu.items.add(openItem)
            menuBar.menus.add(menu)

            // Now create the player
            val player = Player(
                mediaPath
            ).apply {
                top = menuBar
            }

            // Create the scene and set it to the JFXPanel
            val scene = Scene(player, size.width.toDouble(), size.height.toDouble())
            jfxPanel.scene = scene
            player.player.play()
        }

        onCreate()
        onDispose {
            onDestroy()
            composeWindow.remove(jPanel)
        }
    }
}