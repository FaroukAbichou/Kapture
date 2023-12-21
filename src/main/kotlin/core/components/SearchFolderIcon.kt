package core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.ImageResource

@Composable
fun SearchFolderIcon(
    modifier: Modifier,
    onClick: () -> Unit = {},
) {
    Image(
        painter = painterResource(ImageResource.image.resourceId),
        contentDescription = "Search Folder Icon",
        modifier = modifier
            .size(24.dp)
            .noRippleClickable {
                onClick()
            }
    )
}
