package record.settings.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import record.settings.presentation.event.SettingsEvent

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
