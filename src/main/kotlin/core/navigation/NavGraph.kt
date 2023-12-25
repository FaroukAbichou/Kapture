package core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import record.audio.presentation.AudioScreen
import record.audio.presentation.AudioViewModel
import record.audio.presentation.event.AudioEvent
import record.home.presentation.HomeScreen
import record.home.presentation.HomeViewModel
import record.home.presentation.event.HomeEvent
import record.image.presentation.ImageScreen
import record.image.presentation.ImageViewModel
import record.image.presentation.event.ImageEvent
import record.settings.presentation.SettingsScreen
import record.settings.presentation.SettingsViewModel
import record.settings.presentation.event.SettingsEvent
import record.video.presentation.VideoScreen
import record.video.presentation.VideoViewModel

class NavGraph {
    object HomeTab : Tab {
        private fun readResolve(): Any = HomeTab
        private val viewModel: HomeViewModel = HomeViewModel()

        override val options: TabOptions
            @Composable
            get() {
                val title = "Home"
                val icon = rememberVectorPainter(Icons.Default.Home)

                return remember {
                    TabOptions(
                        index = 0u,
                        title = title,
                        icon = icon
                    )
                }
            }

        @Composable
        override fun Content() {
            val state by viewModel.state.collectAsState()
            val navigator = LocalNavigator.currentOrThrow

            HomeScreen(
                state = state,
                onEvent = {
                    when (it) {
                        is HomeEvent.NavigateToSettings -> {
                            navigator.push(SettingsTab)
                        }

                        else -> viewModel.onEvent(it)
                    }
                }
            )
        }
    }
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
    object VideosTab : Tab {
        private fun readResolve(): Any = VideosTab
        private val viewModel: VideoViewModel = VideoViewModel()

        override val options: TabOptions
            @Composable
            get() {
                val title = "Videos"
                val icon = rememberVectorPainter(Icons.Default.Home)

                return remember {
                    TabOptions(
                        index = 0u,
                        title = title,
                        icon = icon
                    )
                }
            }

        @Composable
        override fun Content() {
            val state by viewModel.state.collectAsState()
            val navigator = LocalNavigator.currentOrThrow

            VideoScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }
    }
    object AudiosTab : Tab {
        private fun readResolve(): Any = AudiosTab
        private val viewModel: AudioViewModel = AudioViewModel()

        override val options: TabOptions
            @Composable
            get() {
                val title = "Audios"

                return remember {
                    TabOptions(
                        index = 0u,
                        title = title,
                    )
                }
            }

        @Composable
        override fun Content() {
            val state by viewModel.state.collectAsState()
            val navigator = LocalNavigator.currentOrThrow

            AudioScreen(
                state = state,
                onEvent = {
                    when (it) {
                        AudioEvent.GetAudios ->{}

                        else -> viewModel.onEvent(it)
                    }
                }
            )
        }
    }

    object ImagesTab : Tab {
        private fun readResolve(): Any = ImagesTab
        private val viewModel: ImageViewModel = ImageViewModel()

        override val options: TabOptions
            @Composable
            get() {
                val title = "Images"

                return remember {
                    TabOptions(
                        index = 0u,
                        title = title,
                    )
                }
            }

        @Composable
        override fun Content() {
            val state by viewModel.state.collectAsState()
            val navigator = LocalNavigator.currentOrThrow

            ImageScreen(
                state = state,
                onEvent = {
                    when (it) {
                        ImageEvent.GetImages ->{

                        }

                        else -> viewModel.onEvent(it)
                    }
                },
            )
        }
    }
}