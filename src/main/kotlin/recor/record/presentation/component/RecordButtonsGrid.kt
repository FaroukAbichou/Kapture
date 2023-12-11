package recor.record.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.ImageResource
import core.util.TimeHelper.getRecordingOutputFileName
import probe.domain.WindowBounds
import recor.video.domain.model.RecordSettings
import recor.video.presentation.event.VideoEvent
import recor.video.presentation.state.VideoState

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
                            bounds = WindowBounds(
                                x1 = 0,
                                y1 = 0,
                                x2 = 400,
                                y2 = 400
                            )
                        )
                    )
                },
                imageResource = ImageResource.crop,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Full Screen",
                onClick = {
//                    onEvent(
//                        RecordEvent.RecordSelectedScreen(
//                            config = ,
//                        )
//                    )
                },
                imageResource = ImageResource.display,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Specific Window",
                onClick = {
                    onEvent(
                        VideoEvent.Record(
                            config = RecordSettings(
                                outputFile = getRecordingOutputFileName()
                            ),
                        )
                    )
                    println( "currentTime: ${getRecordingOutputFileName()}")
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
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Audio only",
                onClick = {
                    onEvent(
                        VideoEvent.RecordAudio(
                            config = RecordSettings(
                                audioSource = "default"
                            )
                        )
                    )
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
                modifier = Modifier
                    .padding(8.dp)
            )
        }

    }
}
