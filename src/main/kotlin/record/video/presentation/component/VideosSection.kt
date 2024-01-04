package record.video.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.dp
import core.components.KpFilterDropdown
import core.components.KpSortDropdown
import record.home.presentation.component.GetStartedSection
import record.home.presentation.component.KpSearchBar
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun VideosSection(
    modifier: Modifier = Modifier,
    state: VideoState,
    onEvent: (VideoEvent) -> Unit,
    composeWindow: ComposeWindow,
) {
    var searchedVideos by remember {
        mutableStateOf(state.videos)
    }
    val filterOptions = listOf("All", "Movies", "TV Shows")
    val sortOptions = listOf("Name", "Date Added", "Size", "Duration")

    var searchQuery by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit){
        onEvent(
            VideoEvent.SelectScreen(
                screenId = state.screens.first().id
            )
        )
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .fillMaxSize()
    ) {
        GetStartedSection(
            state = state,
            onEvent= onEvent
        )
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
                    searchedVideos = state.videos.filter { video ->
                        video.name.contains(query, ignoreCase = true)
                    }
                }
            )
            KpFilterDropdown(
                modifier = Modifier,
                filterOptions = filterOptions,
                onFilter = {
                    searchedVideos = if (it == "All") state.videos else state.videos.filter { video ->
                        video.name.contains(it, ignoreCase = true)
                    }
                }
            )
            KpSortDropdown(
                modifier = Modifier,
                sortOptions = sortOptions,
                onSort = {
                    searchedVideos = when (it) {
                        "Name" -> searchedVideos.sortedBy { audio -> audio.name }
                        "Date Added" -> searchedVideos.sortedBy { audio -> audio.dateCreated }
                        "Size" -> searchedVideos.sortedBy { audio -> audio.size }
                        "Duration" -> searchedVideos.sortedBy { audio -> audio.duration }

                        else -> emptyList()
                    }
                }
            )
        }

        LazyColumn(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start
        ) {
            searchedVideos.forEach { item ->
                item {
                    KpVideoItem(
                        modifier = Modifier,
                        composeWindow = composeWindow,
                        video = item,
                        onClick = {}
                    )
                }
            }
        }
    }
}