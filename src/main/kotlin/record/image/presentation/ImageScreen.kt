package record.image.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.KpProgressIndicator
import record.image.presentation.component.ImagesSection
import record.image.presentation.event.ImageEvent
import record.image.presentation.state.ImageState

@Composable
fun ImageScreen(
    state: ImageState,
    onEvent: (ImageEvent) -> Unit,
) {
    Scaffold(
        topBar = {

        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        if (state.isLoading) {
            KpProgressIndicator()
        } else {
            ImagesSection(
                modifier = Modifier
                    .padding(paddingValues),
                state = state,
                onEvent = onEvent
            )
        }
    }
}
