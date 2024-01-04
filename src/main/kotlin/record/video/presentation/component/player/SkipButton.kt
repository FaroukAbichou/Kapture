package record.video.presentation.component.player

import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SkipButton(
    player: Player,
    skipSeconds: Int,
    icon: ImageVector,
    contentDesc: String
) {
    Button(
        onClick = { player.skip(skipSeconds) }
    ) {
        Icon(imageVector = icon, contentDescription = contentDesc)
    }
}