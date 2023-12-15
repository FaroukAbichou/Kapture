
import androidx.compose.runtime.*
import androidx.compose.ui.window.DialogWindow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.FileDialog
import java.awt.Frame
import javax.swing.SwingUtilities

@Composable
fun FileDialog(
    title: String,
    isOpen: Boolean,
    fileExtensions: Set<String>,
    onResult: (String?) -> Unit
) {
    var dialog by remember { mutableStateOf<FileDialog?>(null) }

    LaunchedEffect(isOpen) {
        if (isOpen) {
            withContext(Dispatchers.IO) {
                dialog = FileDialog(null as Frame?, title).apply {
                    isMultipleMode = false
                    mode = FileDialog.LOAD
//                    filenameFilter = FilenameFilter { _, name ->
//                        fileExtensions.any { name.endsWith(it) }
//                    }
                }

                SwingUtilities.invokeLater {
                    val files = dialog?.files?.map { it.path }
                    onResult(files?.firstOrNull())
                    dialog = null
                }
            }
        }
    }

    dialog?.let {
        DialogWindow(
            onCloseRequest = { onResult(null) },
            title = title,
            content = {

            }
        )
    }
}
