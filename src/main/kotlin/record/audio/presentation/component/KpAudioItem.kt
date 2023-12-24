package record.audio.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import record.audio.domain.model.Audio
import core.components.noRippleClickable

@Composable
fun KpAudioItem(
    modifier: Modifier,
    audio : Audio,
    onClick: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .noRippleClickable {
                onClick()
            }
    ) {
        Image(
            bitmap = audio.thumbnail,
            contentDescription = audio.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text(
                text = audio.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier,
            )

            Text(
                text = audio.getDescription(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier,
            )
        }

    }
}