package record.video.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import core.components.KpFilterDropdown
import core.util.FilePaths
import record.home.presentation.component.KpSearchBar
import record.home.presentation.component.noRippleClickable
import record.video.domain.model.Video
import record.video.presentation.event.VideoEvent
import record.video.presentation.state.VideoState

@Composable
fun VideosSection(
    state: VideoState,
    onEvent: (VideoEvent) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEvent(VideoEvent.GetVideosByPath(FilePaths.VideosPath))
    }

    var searchedVideos by remember {
        mutableStateOf(state.videos)
    }
    val filterOptions = listOf("All", "Movies", "TV Shows")

    var searchQuery by remember {
        mutableStateOf("")
    }

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
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
            modifier = Modifier.height(200.dp).width(200.dp),
            filterOptions = filterOptions,
            onFilter = {
                searchedVideos =if (it == "All") state.videos else state.videos.filter { video ->
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
                    onClick = {
//                        onEvent(VideoEvent.NavigateToVideoDetailsScreen(item))
                    }
                )
            }
        }
    }
}

@Composable
fun KpVideoItem(
    modifier: Modifier,
    video: Video,
    onClick: () -> Unit = {},
) {
    Image(
        bitmap = video.thumbnail,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(200.dp)
            .width(200.dp)
            .noRippleClickable {
                onClick()
            }
    )
}