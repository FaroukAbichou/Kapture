package record.presentation.event

sealed class RecordEvent {
    data object Load : RecordEvent()
}
