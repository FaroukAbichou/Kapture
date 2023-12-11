package recor.home.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import probe.domain.ProbRepository
import probe.domain.WindowPlacement
import probe.domain.model.Screen
import recor.home.presentation.event.HomeEvent
import recor.home.presentation.event.RecordingFrameEvent
import recor.home.presentation.state.HomeState

class HomeViewModel : KoinComponent {

    private val probRepository: ProbRepository by inject()

    private val _state = MutableStateFlow(HomeState())

    val state: StateFlow<HomeState> = _state.asStateFlow()


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetScreens -> {

            }
        }
    }

    fun onRecordingFrameEvent(event: RecordingFrameEvent) {
        when (event) {
            is RecordingFrameEvent.UpdateWindowPlacement -> {
                _state.value = _state.value.copy(
                    recordingArea = WindowPlacement(
                        x = event.x,
                        y = event.y,
                        width = event.width,
                        height = event.height
                    ),
                    selectedScreen = Screen(
                        id = if (event.x >= 0) "0" else "1",
                        name = "Screen ${if (event.x >= 0) "0" else "1"}",
                        width = _state.value.selectedScreen.width,
                        height = _state.value.selectedScreen.height,
                    ),
                )
                println( event.x)
                println( _state.value.selectedScreen.id)
            }
        }
    }


}
