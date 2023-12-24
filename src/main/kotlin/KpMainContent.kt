import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import core.components.KpSideNavigationBar
import record.audio.presentation.AudiosTab
import record.home.presentation.HomeTab
import record.image.presentation.ImagesTab
import record.settings.presentation.SettingsTab
import record.video.presentation.VideosTab

@Composable
fun KpMainContent() {
    TabNavigator(HomeTab) { navigator ->
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            KpSideNavigationBar(
                modifier = Modifier,
                onHomeClick = {
                    navigator.current = HomeTab
                },
                onVideoClick = {
                    navigator.current = VideosTab
                },
                onImageClick = {
                    navigator.current = ImagesTab
                },
                onAudioClick = {
                    navigator.current = AudiosTab

                },
                onSettingsClick = {
                    navigator.current = SettingsTab
                },
            )

            CurrentTab()
        }
    }
}