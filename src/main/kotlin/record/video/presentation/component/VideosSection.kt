package record.video.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.util.FilePaths
import record.video.domain.model.Video
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun VideosSection(
    state: VideoState,
    onEvent: (VideoEvent) -> Unit
) {
    LaunchedEffect(Unit){
        onEvent(VideoEvent.GetVideosByPath(FilePaths.VideosPath))
    }

    var SearchedVideos by remember {
        mutableStateOf(state.videos)
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
    ) {

        SearchScreen(
            searchQuery = "",
            searchResults = SearchedVideos,
            onSearchQueryChange = {
                SearchedVideos = state.videos.filter { video ->
                    video.name.contains(it, ignoreCase = true)
                }
            }
        )
        LazyColumn {
            SearchedVideos.forEach { video: Video ->
                item{
                    Text(
                        text = video.name,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier,
                    )
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchQuery: String,
    searchResults: List<Video>,
    onSearchQueryChange: (String) -> Unit
) {
    SearchBar(
        query = searchQuery,
        onQueryChange = onSearchQueryChange,
        onSearch = {},
        placeholder = {
            Text(text = "Search movies")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null
            )
        },
        trailingIcon = {},
        content = {},
        active = true,
        onActiveChange = {},
        tonalElevation = 0.dp
    )
}