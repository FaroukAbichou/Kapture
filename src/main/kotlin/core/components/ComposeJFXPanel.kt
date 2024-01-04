package core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.round
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import java.awt.BorderLayout
import javax.swing.JPanel

@Composable
fun ComposeJFXPanel(
    modifier: Modifier,
    jfxPanel: JFXPanel,
    composeWindow: ComposeWindow,
    onCreate: () -> Unit,
    onDestroy: () -> Unit = {}
) {
    val jPanel = remember { JPanel(BorderLayout()) }
    val density = LocalDensity.current.density

        //intialize the paltformRunLater


    Box(
        modifier = modifier
            .onGloballyPositioned { childCoordinates ->
                val coordinates = childCoordinates.parentCoordinates!!
                val location = coordinates.localToWindow(Offset.Zero).round()
                platformRunLater {
                    jPanel.setBounds(
                        (location.x / density).toInt(),
                        (location.y / density).toInt(),
                        (coordinates.size.width / density).toInt(),
                        (coordinates.size.height / density).toInt()
                    )
                }
            }
    )

    DisposableEffect(jPanel) {
        platformRunLater {
        composeWindow.add(jPanel)
        jPanel.layout = BorderLayout()
        jPanel.add(jfxPanel,BorderLayout.CENTER)
            onCreate()
        }

        onDispose {
            onDestroy()
            composeWindow.remove(jPanel)
        }
    }
}

fun platformRunLater(action: () -> Unit) {
    Platform.runLater(action)
}