package record.image.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import record.image.presentation.component.ImagesSection

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

        ImagesSection(
            state = state,
            onEvent = {
                when (it) {

                    else -> viewModel.onEvent(it)
                }
            }
        )
    }
}