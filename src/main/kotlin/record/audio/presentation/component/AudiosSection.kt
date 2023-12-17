package record.audio.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.util.FilePaths
import record.audio.domain.model.Audio
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState
import record.home.presentation.component.KpSearchBar

@Composable
fun AudiosSection(
    state: AudioState,
    onEvent: (AudioEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(AudioEvent.GetAudiosByPath(FilePaths.AudiosPath))
    }

    var searchedAudios by remember {
        mutableStateOf(state.audios)
    }
    var searchQuery by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
    ) {
        KpSearchBar(
            modifier = Modifier,
            searchQuery = searchQuery,
            searchResults = searchedAudios,
            onSearchQueryChange = { query ->
                searchQuery = query
                searchedAudios = state.audios.filter { video ->
                    video.name.contains(query, ignoreCase = true)
                }
            }
        )
    }
    LazyColumn(
        modifier = Modifier,
        horizontalAlignment = Alignment.Start
    ) {
        state.audios.forEach { audio: Audio ->
            item {
                Text(
                    text = audio.name,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier,
                )
            }
        }
    }
}