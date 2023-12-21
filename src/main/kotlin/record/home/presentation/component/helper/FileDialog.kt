
import androidx.compose.runtime.*
import java.awt.FileDialog
import java.awt.Frame
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.FilenameFilter
import javax.swing.SwingUtilities

@Composable
fun FileDialog(
    title: String,
    isOpen: MutableState<Boolean>,
    fileExtensions: Set<String>,
    onResult: (String?) -> Unit
) {
    var dialog by remember { mutableStateOf<FileDialog?>(null) }

    if (isOpen.value && dialog == null) {
        SwingUtilities.invokeLater {
            dialog = FileDialog(null as Frame?, title).apply {
                isMultipleMode = false
                mode = FileDialog.LOAD
                filenameFilter = FilenameFilter { _, name ->
                    fileExtensions.any { name.endsWith(it) }
                }
                isVisible = true
            }
            dialog?.addWindowListener(object : WindowAdapter() {
                override fun windowClosing(e: WindowEvent?) {
                    println( "FileDialog file: ${dialog?.file}")
                    val file = dialog?.file
                    SwingUtilities.invokeLater {
                        onResult(file)
                    }
                    isOpen.value = false
                    dialog = null
                }
            })

        }

    }

    if (!isOpen.value) {
        dialog?.let {
            SwingUtilities.invokeLater {
                it.dispose()
                dialog = null
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            dialog?.let {
                SwingUtilities.invokeLater {
                    it.dispose()
                    dialog = null
                }
            }
        }
    }
}
