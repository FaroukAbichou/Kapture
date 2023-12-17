package core.components

import FileDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.ImageResource
import record.home.presentation.component.noRippleClickable

@Composable
fun SearchFolderIcon() {
    var showFileDialog by remember {
        mutableStateOf(false)
    }
    var folderLocation by remember {
        mutableStateOf("")
    }
    Image(
        painter = painterResource(ImageResource.image.resourceId),
        contentDescription = "Search Folder Icon",
        modifier = Modifier
            .size(24.dp)
            .noRippleClickable {
                showFileDialog = true
                println( "showFileDialog: $showFileDialog")
            }
    )

    FileDialog(
        title = "Select output location",
        isOpen = showFileDialog,
        fileExtensions = setOf("mp4"),
        onResult = {
            if (it != null) {
                folderLocation = it
            }
            showFileDialog = false
        }
    )

}
