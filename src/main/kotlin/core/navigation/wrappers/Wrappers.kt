package core.navigation.wrappers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import record.home.presentation.HomeScreen
import record.home.presentation.HomeViewModel
import record.home.presentation.event.HomeEvent
import record.settings.presentation.SettingsScreen
import record.settings.presentation.SettingsViewModel
import record.settings.presentation.event.SettingsEvent

object HomeTab : Tab {
    private fun readResolve(): Any = HomeTab

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = androidx.compose.ui.graphics.Color.Gray
                )
        ) {
            Text(text = "Home")
        }
    }
}

object SettingsTab : Tab {
    private fun readResolve(): Any = SettingsTab

    override val options: TabOptions
        @Composable
        get() {
            val title = "Settings"
            val icon = rememberVectorPainter(Icons.Default.Settings)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = androidx.compose.ui.graphics.Color.Red
                )
        ) {
            Text(text = "Settings")
        }
    }
}

internal class HomeScreenWrapper : Screen {

    private val viewModel : HomeViewModel = HomeViewModel()
//    private val videoViewModel = remember { VideoViewModel() }
//    private val imageViewModel = remember { ImageViewModel() }
//    private val audioViewModel = remember { AudioViewModel() }

//    private val videoState = videoViewModel.state.collectAsState()
//    private val imageState = imageViewModel.state.collectAsState()
//    private val audioState = audioViewModel.state.collectAsState()


    @Composable
    override fun Content() {
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        HomeScreen(
            state = state,
            onEvent = {
                when(it){
                    is HomeEvent.NavigateToSettings -> {
                        navigator.push(SettingsScreenWrapper(it.arg))
                    }

                    else -> viewModel.onEvent(it)
                }
            }
        )

//        if (
//            videoState.value.isRecordSection ||
//            imageState.value.isRecordSection
//        ) RecordingFrame(
//            modifier = Modifier,
//        ) { x: Int, y: Int, height: Int, width: Int ->
//            videoViewModel.onRecordingFrameEvent(
//                RecordingFrameEvent.UpdateWindowPlacement(
//                    x = x, y = y, height = height, width = width,
//                )
//            )
//            imageViewModel.onRecordingFrameEvent(
//                RecordingFrameEvent.UpdateWindowPlacement(
//                    x = x, y = y, height = height, width = width,
//                )
//            )
//        }
    }
}

internal class SettingsScreenWrapper(val arg :String) : Screen {

    @Composable
    override fun Content() {
        val viewModel = SettingsViewModel()
        val state by viewModel.state.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        SettingsScreen(
            state = state,
            onEvent = {
                when(it){
                    is SettingsEvent.NavigateToHome -> {
                        navigator.pop()
                    }
                    else -> viewModel.onEvent(it)
                }
            }
        )
    }
}