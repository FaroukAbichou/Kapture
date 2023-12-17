package record.image.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import core.util.FilePaths
import record.home.presentation.component.KpSearchBar
import record.image.domain.model.Image
import record.image.presentation.event.ImageEvent
import record.image.presentation.state.ImageState

@Composable
fun ImagesSection(
    state: ImageState,
    onEvent: (ImageEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(ImageEvent.GetImageByPath(FilePaths.ImagesPath))
    }
    var searchedImages by remember {
        mutableStateOf(state.images)
    }
    var searchQuery by remember {
        mutableStateOf("")
    }

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
    ) {

        KpSearchBar(
            searchQuery = searchQuery,
            searchResults = searchedImages,
            onSearchQueryChange = {
                searchQuery = it
                searchedImages = state.images.filter { video ->
                    video.name.contains(it, ignoreCase = true)
                }
            }
        )
        LazyColumn {
            state.images.forEach { image: Image ->
                item {
                    Text(
                        text = image.name,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier,
                    )
                }
            }
        }
    }
}