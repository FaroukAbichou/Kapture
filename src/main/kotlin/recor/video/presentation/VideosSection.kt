package recor.video.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import core.util.FilePaths
import recor.video.presentation.event.VideoEvent
import recor.video.presentation.state.Video

@Composable
fun VideosSection() {
    val videoViewModel = rememberSaveable { VideoViewModel() }
    val state = videoViewModel.state.collectAsState()

    LaunchedEffect(Unit){
        videoViewModel.onEvent(VideoEvent.GetVideosByPath(FilePaths.VideosPath))
    }
    LazyColumn {
        state.value.videos.forEach { video: Video ->
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
