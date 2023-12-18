package record.image.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import record.home.presentation.component.noRippleClickable
import record.image.domain.model.Image

@Composable
fun KpImageItem(
    modifier: Modifier,
    image : Image,
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
            bitmap = image.thumbnail,
            contentDescription = image.name,
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
                text = image.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier,
            )

            Text(
                text = image.getDescription(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier,
            )
        }

    }
}