package record.home.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.SelectOutputLocationSection
import record.home.presentation.event.HomeEvent
import record.home.presentation.state.HomeState

@Composable
fun HomeContent(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectOutputLocationSection(
            folderLocation = {
//                onEvent(HomeEvent.SelectOutputLocation)
            },
            currentLocation = state.outputLocation,
            modifier = Modifier
                .padding(16.dp)
        )

        Button(
            onClick = {
                onEvent(HomeEvent.NavigateToSettings("hiii"))
            }
        ) {
            Text("Settings")
        }
//        RecordContent(
//            videoState = videoState,
//            imageState = imageState,
//            audioState = audioState,
//            onVideoEvent = onVideoEvent,
//            onImageEvent = onImageEvent,
//            onAudioEvent = onAudioEvent,
//        )
    }

}
