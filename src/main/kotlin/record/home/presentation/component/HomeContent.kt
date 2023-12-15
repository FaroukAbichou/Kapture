package record.home.presentation.component

import FileDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.ImageResource
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState
import record.home.presentation.component.helper.noRippleClickable
import record.image.presentation.event.ImageEvent
import record.image.presentation.state.ImageState
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun HomeContent(
    videoState: VideoState,
    imageState: ImageState,
    audioState: AudioState,
    onVideoEvent: (VideoEvent) -> Unit,
    onImageEvent: (ImageEvent) -> Unit,
    onAudioEvent: (AudioEvent) -> Unit,
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectOutputLocationSection(
            modifier = Modifier
                .padding(16.dp)
        )

        RecordContent(
            videoState = videoState,
            imageState = imageState,
            audioState = audioState,
            onVideoEvent = onVideoEvent,
            onImageEvent = onImageEvent,
            onAudioEvent = onAudioEvent,
        )
    }

}

@Composable
fun SelectOutputLocationSection(
    modifier: Modifier
) {
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
                    value = folderLocation,
                    onValueChange = {
                        folderLocation = it
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
                        SearchFolderIcon()
                    },
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }

    }
}

@Composable
fun SearchFolderIcon() {

    var showFileDialog by remember {
        mutableStateOf(false)
    }
    Image(
        painter = painterResource(ImageResource.image.resourceId),
        contentDescription = "Search Folder Icon",
        modifier = Modifier
            .size(24.dp)
            .noRippleClickable {
            showFileDialog = true
            }
    )

        FileDialog(
            title = "Select output location",
            isOpen = showFileDialog,
            fileExtensions = ,
            onResult = {
                showFileDialog = false
            }
        )

}
