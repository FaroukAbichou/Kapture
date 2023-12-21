package record.video.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.components.KpFilterDropdown
import record.home.presentation.component.KpSearchBar
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun VideosSection(
    modifier : Modifier = Modifier,
    state: VideoState,
    onEvent: (VideoEvent) -> Unit,
) {

    var searchedVideos by remember {
        mutableStateOf(state.videos)
    }
    val filterOptions = listOf("All", "Movies", "TV Shows")

    var searchQuery by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Top,
        modifier = modifier
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
        }

        LazyColumn(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start
        ) {
            searchedVideos.forEach { item ->
                item {
                    KpVideoItem(
                        modifier = Modifier,
                        video = item,
                        onClick = {}
                    )
                }
            }
        }
    }
}