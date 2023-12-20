
import androidx.compose.runtime.*
import java.awt.FileDialog
import java.awt.Frame
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
                val file = dialog?.file
                SwingUtilities.invokeLater {
                    onResult(file)
                }
                isOpen.value = false
                dialog = null
            }

        }
//        dialog?.addWindowListener(object : WindowAdapter() {
//            override fun windowClosing(e: WindowEvent?) {
//                // When the dialog is closing, retrieve the selected file and invoke onResult.
//                println( "FileDialog file: ${dialog?.file}")
//                val file = dialog?.file // Retrieve the selected file path.
//                SwingUtilities.invokeLater {
//                    onResult(file) // Provide the result to the callback.
//                }
//                isOpen.value = false // Reset the open state.
//                dialog = null // Dispose of the dialog.
//            }
//        })

    }

    if (!isOpen.value) {
        dialog?.let {
            SwingUtilities.invokeLater {
                it.dispose()
                dialog = null // Reset the dialog reference.
            }
        }
    }

    // This DisposableEffect ensures that the dialog is properly disposed of
    // when this Composable is removed from the composition.
    DisposableEffect(Unit) {
        onDispose {
            dialog?.let {
                SwingUtilities.invokeLater {
                    it.dispose() // Dispose of the dialog on the EDT.
                    dialog = null // Reset the dialog reference.
                }
            }
        }
    }
}
