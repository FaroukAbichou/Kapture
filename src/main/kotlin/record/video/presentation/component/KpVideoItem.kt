package record.video.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.dp
import core.components.noRippleClickable
import record.video.domain.model.Video

@Composable
fun KpVideoItem(
    modifier: Modifier,
    video: Video,
    onClick: () -> Unit = {},
    composeWindow: ComposeWindow,
) {

        // Your app content
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .noRippleClickable {
                    onClick()
                }
        ) {
//            VideoPlayer(
//                videoPath = video.path,
//                modifier = Modifier
//                    .size(100.dp),
//                contentScale = ContentScale.Crop,
//                composeWindow =composeWindow
//            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = video.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier,
                )

                Text(
                    text = video.getDescription(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier,
                )
            }
        }
    }