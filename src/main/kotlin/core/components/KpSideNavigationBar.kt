package core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.ImageResource

@Composable
fun KpSideNavigationBar(
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
            onClick = onHomeClick
        )
        KpIconButton(
            imageResource = ImageResource.video,
            onClick = onVideoClick
        )
        KpIconButton(
            imageResource = ImageResource.image,
            onClick = onImageClick
        )
        KpIconButton(
            imageResource = ImageResource.waveformCircle,
            onClick = onAudioClick
        )
        KpIconButton(
            imageResource = ImageResource.setting,
            onClick = onSettingsClick
        )

    }
}

@Composable
fun KpIconButton(
    imageResource: ImageResource,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        elevation = null,
        border = null,
        contentPadding = PaddingValues(0.dp),
        interactionSource = MutableInteractionSource(),
        modifier = Modifier
    ){
        Image(
            painter = painterResource(imageResource.resourceId),
            contentDescription = imageResource.name,
            modifier = Modifier
                .size(32.dp)
                .padding(8.dp)
        )
    }
}