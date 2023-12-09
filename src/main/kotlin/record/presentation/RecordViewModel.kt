package record.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import record.domain.RecordRepository
import record.presentation.event.RecordEvent
import record.presentation.state.RecordState
import screen.domain.ScreenRepository

class RecordViewModel : KoinComponent {

    private val recordRepository: RecordRepository by inject()
    private val screenRepository: ScreenRepository by inject()

    private val _state = MutableStateFlow(RecordState())

    val state: StateFlow<RecordState> = _state.asStateFlow()


    fun onEvent(event: RecordEvent) {
        when (event) {
            RecordEvent.Load -> TODO()
        }
    }

}
