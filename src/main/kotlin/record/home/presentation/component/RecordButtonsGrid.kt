package record.home.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.ImageResource
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun RecordButtonsGrid(
    state: VideoState,
    onEvent: (VideoEvent) -> Unit,
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RecordOptionButton(
                text = "Record",
                onClick = {
                    onEvent(VideoEvent.Record)
                          },
                imageResource = ImageResource.lockDisplay,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Stop Recording",
                onClick = { onEvent(VideoEvent.StopRecording) },
                imageResource = ImageResource.lockDisplay,
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}
