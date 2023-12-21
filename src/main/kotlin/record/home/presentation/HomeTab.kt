package record.home.presentation

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
import record.home.presentation.event.HomeEvent
import record.settings.presentation.SettingsTab

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