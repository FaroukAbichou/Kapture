package recor.record.presentation.state

data class Record(
    val id: Int,
    val name: String,
    val description: String,
    val type: RecordType,
)
