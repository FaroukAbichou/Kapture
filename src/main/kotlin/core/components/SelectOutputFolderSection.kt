package core.components

import FileDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SelectOutputFolderSection(
    modifier: Modifier,
    currentLocation: String,
    onSelectLocation: (String) -> Unit,
) {
    val showFileDialog = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier,
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
                    onSelectLocation(value)
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
                            showFileDialog.value = true
                        }
                    )
                },
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }

    FileDialog(
        title = "Select output location",
        isOpen = showFileDialog,
        fileExtensions = null,
        onResult = { location ->
            if (location != null) {
                onSelectLocation(location)
            }
        }
    )
}
