package record.image.presentation.state


import record.image.domain.model.Image

data class ImageState(
    val images: List<Image> = emptyList(),
    val isRecordSection: Boolean = false,
)
