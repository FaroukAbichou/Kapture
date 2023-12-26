
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import java.awt.FileDialog
import javax.swing.JFrame

@Composable
fun FileDialog(
    title: String,
    isOpen: MutableState<Boolean>,
    fileExtensions: Set<String>?,
    onResult: (String?) -> Unit,
) {
    if (isOpen.value) {
        val frame = JFrame().apply { isVisible = true }
        val dialog = FileDialog(frame, title, FileDialog.LOAD)

        // Set system property for file extension filter on macOS
        // Note: This might not work on other operating systems
        System.setProperty("apple.awt.fileDialogForDirectories", (fileExtensions == null).toString())
        if (!fileExtensions.isNullOrEmpty()) {
            dialog.file = fileExtensions.joinToString(separator = ";") { "*$it" }
        }
        dialog.isVisible = true
        isOpen.value = false // Close the dialog after selection

        val selectedFile = if (dialog.file != null) {
            "${dialog.directory}${dialog.file}"
        } else null

        onResult(selectedFile)
        frame.dispose() // Dispose the frame to avoid memory leaks
    }
}
