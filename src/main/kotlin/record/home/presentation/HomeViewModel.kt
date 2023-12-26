package record.home.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import probe.camera.domain.CameraRepository
import record.home.presentation.event.HomeEvent
import record.home.presentation.state.HomeState

class HomeViewModel : KoinComponent {

    private val cameraRepository: CameraRepository by inject()

    private val _state = MutableStateFlow(HomeState())

    val state: StateFlow<HomeState> = _state.asStateFlow()


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetScreens -> {

            }

            else -> {}
        }
    }

}
