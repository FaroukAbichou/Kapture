package record.audio.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.components.KpProgressIndicator
import record.audio.presentation.component.AudiosSection
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState

@Composable
fun AudioScreen(
    state: AudioState,
    onEvent: (AudioEvent) -> Unit,
) {
    Scaffold(
        topBar = {

        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        AudiosScreenContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            onEvent = onEvent
        )
    }
}

@Composable
fun AudiosScreenContent(
    modifier :Modifier,
    state: AudioState,
    onEvent: (AudioEvent) -> Unit
){
    if (state.isLoading){
        KpProgressIndicator()
    }else {
        AudiosSection(
            modifier = modifier,
            state = state,
            onEvent = onEvent
        )
    }
}