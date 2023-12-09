package record.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.ImageResource
import record.presentation.event.RecordEvent
import record.presentation.state.RecordState

@Composable
fun RecordButtonsGrid(
    state: RecordState,
    onEvent: (RecordEvent) -> Unit
) {
    Column (
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
//                    onEvent(RecordEvent.RecordTypeSelected(index))
                },
                imageResource = ImageResource.crop,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Full Screen",
                onClick = {
//                    onEvent(RecordEvent.RecordTypeSelected(index))
                },
                imageResource = ImageResource.display,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Specific Window",
                onClick = {
//                    onEvent(RecordEvent.RecordTypeSelected(index))
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
//                    onEvent(RecordEvent.RecordTypeSelected(index))
                },
                imageResource = ImageResource.cameraLens,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "Audio only",
                onClick = {
//                    onEvent(RecordEvent.RecordTypeSelected(index))
                },
                imageResource = ImageResource.waveformCircle,
                modifier = Modifier
                    .padding(8.dp)
            )
            RecordOptionButton(
                text = "All Windows",
                onClick = {
//                    onEvent(RecordEvent.RecordTypeSelected(index))
                },
                imageResource = ImageResource.multipleDisplay,
                modifier = Modifier
                    .padding(8.dp)
            )
        }

    }
}
