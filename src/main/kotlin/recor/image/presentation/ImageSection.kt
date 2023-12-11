package recor.image.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import core.util.FilePaths
import recor.image.presentation.event.ImageEvent
import recor.image.presentation.state.Image

@Composable
fun ImageSection() {
    val imageViewModel = rememberSaveable { ImageViewModel() }
    val state = imageViewModel.state.collectAsState()

    LaunchedEffect(Unit){
        imageViewModel.onEvent(ImageEvent.GetImageByPath(FilePaths.ImagesPath))
    }
    LazyColumn {
        state.value.images.forEach { image: Image ->
            item{
                Text(
                    text = image.name,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier,
                )
            }
        }
    }
}
