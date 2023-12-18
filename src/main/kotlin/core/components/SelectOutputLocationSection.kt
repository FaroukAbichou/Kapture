package core.components

import FileDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectOutputLocationSection(
    modifier: Modifier,
    currentLocation: String,
    folderLocation: (String) -> Unit
) {
    var showFileDialog by remember {
        mutableStateOf(false)
    }
    var folderLocation by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select Folder to save output",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Output Location",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(16.dp)
                )

                TextField(
                    value = currentLocation,
                    onValueChange = { value ->
                        folderLocation(value)
                    },
                    placeholder = {
                        Text(
                            text = "Select Folder",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    trailingIcon = {
                        SearchFolderIcon(
                            modifier = Modifier
                                .padding(8.dp),
                            onClick = {
                                showFileDialog = true
                            }
                        )
                    },
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }

    FileDialog(
        title = "Select output location",
        isOpen = showFileDialog,
        fileExtensions = setOf("mp4"),
        onResult = {
            if (it != null) {
                folderLocation = it
            }
//            showFileDialog = false
        }
    )
}
