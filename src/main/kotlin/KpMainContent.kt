
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import core.components.KpSideNavigationBar
import record.audio.presentation.AudiosTab
import record.home.presentation.HomeTab
import record.image.presentation.ImagesTab
import record.settings.presentation.SettingsScreen
import record.settings.presentation.SettingsViewModel
import record.settings.presentation.event.SettingsEvent
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
                    navigator.current = NavGraph.SettingsTab
                },
            )

            CurrentTab()
        }
    }
}

class NavGraph{
    object SettingsTab : Tab {

        private val viewModel = SettingsViewModel()

        private fun readResolve(): Any = SettingsTab

        override val options: TabOptions
            @Composable
            get() {
                val title = "Settings"

                return remember {
                    TabOptions(
                        index = 1u,
                        title = title,
                    )
                }
            }

        @Composable
        override fun Content() {
            val state by viewModel.state.collectAsState()
            val navigator = LocalNavigator.currentOrThrow

            SettingsScreen(
                state = state,
                onEvent = {
                    when (it) {
                        is SettingsEvent.NavigateToHome -> {
                            navigator.pop()
                        }

                        else -> viewModel.onEvent(it)
                    }
                }
            )
        }
    }

}