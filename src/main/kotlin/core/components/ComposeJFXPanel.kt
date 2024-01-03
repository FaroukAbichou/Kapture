package core.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import javafx.embed.swing.JFXPanel
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JPanel

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
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                val windowSize = coordinates.size
                jPanel.preferredSize = Dimension(
                    (windowSize.width / density).toInt(),
                    (windowSize.height / density).toInt()
                )
                jPanel.size = jPanel.preferredSize
                jPanel.minimumSize = jPanel.preferredSize
                jfxPanel.preferredSize = jPanel.preferredSize
            }
        ,
        measurePolicy = { _, _ -> layout(0, 0) {} }
    )

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