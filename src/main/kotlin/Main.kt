
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.sun.javafx.application.PlatformImpl
import core.di.initKoin
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.StackPane
import netscape.javascript.JSObject
import java.awt.BorderLayout
import javax.swing.JPanel

fun main() {
    initKoin().koin
    application(exitProcessOnExit = true) {

        // Required to make sure the JavaFx event loop doesn't finish (can happen when java fx panels in app are shown/hidden)
        val finishListener = object : PlatformImpl.FinishListener {
            override fun idle(implicitExit: Boolean) {}
            override fun exitCalled() {}
        }

        PlatformImpl.addListener(finishListener)

        Window(
            title = "WebView Test",
            resizable = false,
            state = WindowState(
                placement = WindowPlacement.Floating,
                size = DpSize(800.dp, 600.dp)
            ),
            onCloseRequest = {
                PlatformImpl.removeListener(finishListener)
            },
            content = {
                val jfxPanel = remember { JFXPanel() }
                var jsObject = remember<JSObject?> { null }

                Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
                    ComposeJFXPanel(
                        jfxPanel = jfxPanel,
                        composeWindow = window,
                        onCreate = {
                            Platform.runLater {
                                val label = Label("Hello, JavaFX!")
                                val stackPane = StackPane(label) // Using StackPane for center alignment
                                val scene = Scene(stackPane, 800.0, 600.0)
                                jfxPanel.scene = scene
                            }
                        }
                    )
                }            }
        )
    }
}
@Composable
fun ComposeJFXPanel(
    composeWindow: ComposeWindow,
    jfxPanel: JFXPanel,
    onCreate: () -> Unit,
    onDestroy: () -> Unit = {}
) {
    val jPanel = remember { JPanel() }
    val density = LocalDensity.current.density

    Layout(
        content = {},
        modifier = Modifier.onGloballyPositioned { childCoordinates ->
            val coordinates = childCoordinates.parentCoordinates!!
            val location = coordinates.localToWindow(Offset.Zero).round()
            val size = coordinates.size
            jPanel.setBounds(
                (location.x / density).toInt(),
                (location.y / density).toInt(),
                (size.width / density).toInt(),
                (size.height / density).toInt()
            )
            jPanel.validate()
            jPanel.repaint()
        },
        measurePolicy = { _, _ -> layout(0, 0) {} })

    DisposableEffect(jPanel) {
        composeWindow.add(jPanel)
        jPanel.layout = BorderLayout(0, 0)
        jPanel.add(jfxPanel)
        onCreate()
        onDispose {
            onDestroy()
            composeWindow.remove(jPanel)
        }
    }
}
