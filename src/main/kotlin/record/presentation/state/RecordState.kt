package record.presentation.state

data class RecordState(
    val isLoading: Boolean = false,
    val records: List<Record> = emptyList(),
    val error: Throwable? = null,
)

