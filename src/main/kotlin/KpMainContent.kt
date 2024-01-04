
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import core.components.KpSideNavigationBar
import core.navigation.NavGraph

@Composable
fun KpMainContent(composeWindow: ComposeWindow) {
    TabNavigator(NavGraph.HomeTab) { navigator ->
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            KpSideNavigationBar(
                modifier = Modifier,
                onHomeClick = {
                    navigator.current = NavGraph.HomeTab
                },
                onVideoClick = {
                    navigator.current = NavGraph.VideosTab(
                        composeWindow = composeWindow
                    )
                },
                onImageClick = {
                    navigator.current = NavGraph.ImagesTab
                },
                onAudioClick = {
                    navigator.current = NavGraph.AudiosTab

                },
                onSettingsClick = {
                    navigator.current = NavGraph.SettingsTab
                },
            )

            CurrentTab()
        }
    }
}

