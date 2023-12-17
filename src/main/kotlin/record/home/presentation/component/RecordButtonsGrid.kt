package record.home.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.ImageResource
import probe.domain.WindowPlacement
import record.video.domain.model.RecordSettings
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
                text = "Select Recording Area",
                onClick = {
                    onEvent(
                        VideoEvent.RecordSection(
                            config = RecordSettings(),
                            windowPlacement = WindowPlacement.Default
                        )
                    )
                    onEvent(VideoEvent.SelectScreenSection)
                },
                imageResource = ImageResource.crop,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Full Screen",
                onClick = {

                },
                imageResource = ImageResource.display,
                enabled = false,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Specific Window",
                onClick = {
                    onEvent(
                        VideoEvent.Record(
                            config = RecordSettings(),
                            windowPlacement = WindowPlacement.Default
                        )
                    )
                },
                imageResource = ImageResource.lockDisplay,
                modifier = Modifier
                    .padding(8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            RecordOptionButton(
                text = "Device Recording",
                onClick = {
                    onEvent(VideoEvent.RecordDevice)
                },
                imageResource = ImageResource.cameraLens,
                enabled = false,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Audio only",
                onClick = {
//                    onEvent(
//                        VideoEvent.RecordAudio(
//                            config = RecordSettings(),
//                        )
//                    )
                },
                imageResource = ImageResource.waveformCircle,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "All Windows",
                onClick = {
                    onEvent(
                        VideoEvent.RecordAllWindows(
                            config = RecordSettings(
                                audioSource = "default"
                            )
                        )
                    )
                },
                imageResource = ImageResource.multipleDisplay,
                enabled = false,
                modifier = Modifier
                    .padding(8.dp)
            )
        }

    }
}
