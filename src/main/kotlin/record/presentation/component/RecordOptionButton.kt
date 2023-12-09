package record.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.ImageResource

@Composable
fun RecordOptionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    imageResource: ImageResource,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        )
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(imageResource.resourceId),
                contentDescription = text,
                modifier = Modifier
                    .size(56.dp)
            )
            Text(
                text = text,
                modifier = Modifier.width(120.dp),
                style = MaterialTheme.typography.titleSmall.copy(
                    textAlign = TextAlign.Center
                ),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}