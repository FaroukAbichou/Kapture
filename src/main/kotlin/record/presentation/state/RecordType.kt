package record.presentation.state

import core.ImageResource

data class RecordType(
    val id: Int,
    val name: String,
    val description: String,
    val icon: ImageResource,
)