package record.audio.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.KpFilterDropdown
import core.components.KpSortDropdown
import record.audio.presentation.event.AudioEvent
import record.audio.presentation.state.AudioState
import record.home.presentation.component.KpSearchBar

@Composable
fun AudiosSection(
    modifier: Modifier,
    state: AudioState,
    onEvent: (AudioEvent) -> Unit,
) {


    var searchedAudios by remember {
        mutableStateOf(state.audios)
    }

    val filterOptions = listOf("All", "Music", "Podcasts")
    val sortOptions = listOf("Name", "Date Added", "Size", "Duration")

    var searchQuery by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(8.dp)
        ) {
            KpSearchBar(
                modifier = Modifier.width(200.dp),
                searchQuery = searchQuery,
                onSearchQueryChange = { query ->
                    searchQuery = query
                    searchedAudios = state.audios.filter { video ->
                        video.name.contains(query, ignoreCase = true)
                    }
                }
            )
            KpFilterDropdown(
                modifier = Modifier,
                filterOptions = filterOptions,
                onFilter = {
                    searchedAudios = if (it == "All") state.audios else state.audios.filter { video ->
                        video.name.contains(it, ignoreCase = true)
                    }
                }
            )
            KpSortDropdown(
                modifier = Modifier,
                sortOptions = sortOptions,
                onSort = {
                    searchedAudios = when (it) {
                        "Name" -> searchedAudios.sortedBy { audio -> audio.name }
                        "Date Added" -> searchedAudios.sortedBy { audio -> audio.dateCreated }
                        "Size" -> searchedAudios.sortedBy { audio -> audio.size }
                        "Duration" -> searchedAudios.sortedBy { audio -> audio.duration }

                        else -> emptyList()
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start
        ) {
            searchedAudios.forEach { item ->
                item {
                    KpAudioItem(
                        modifier = Modifier,
                        audio = item,
                        onClick = {}
                    )
                }
            }
        }
    }
}