package record.image.presentation.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import core.util.FilePaths
import record.image.domain.model.Image
import record.image.presentation.event.ImageEvent
import record.image.presentation.state.ImageState

@Composable
fun ImagesSection(
    state: ImageState,
    onEvent: (ImageEvent) -> Unit
) {
    LaunchedEffect(Unit){
        onEvent(ImageEvent.GetImageByPath(FilePaths.ImagesPath))
    }
    LazyColumn {
        state.images.forEach { image: Image ->
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