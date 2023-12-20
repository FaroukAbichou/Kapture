package core.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.ImageResource
import record.home.presentation.component.noRippleClickable

@Composable
fun KpNavigationBar(
    modifier: Modifier = Modifier,
    onHomeClick: () -> Unit,
    onVideoClick: () -> Unit,
    onImageClick: () -> Unit,
    onAudioClick: () -> Unit,
    onSettingsClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .padding(24.dp)
    ) {
        KpIconButton(
            imageResource = ImageResource.home,
            onClick = {}
        )
        KpIconButton(
            imageResource = ImageResource.video,
            onClick = {}
        )
        KpIconButton(
            imageResource = ImageResource.image,
            onClick = {}
        )
        KpIconButton(
            imageResource = ImageResource.waveformCircle,
            onClick = {}
        )
        KpIconButton(
            imageResource = ImageResource.setting,
            onClick = {}
        )

    }
}

@Composable
fun KpIconButton(
    imageResource: ImageResource,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(imageResource.resourceId),
        contentDescription = imageResource.name,
        modifier = Modifier
            .size(32.dp)
            .noRippleClickable { onClick() }
    )
}