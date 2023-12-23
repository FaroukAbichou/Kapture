package record.audio.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

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

                    else -> viewModel.onEvent(it)
                }
            }
        )
    }
}