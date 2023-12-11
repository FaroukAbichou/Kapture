package recor.image.presentation.state


import recor.image.domain.model.Image

data class ImageState(
    val images: List<Image> = emptyList(),
    val isRecordSection: Boolean = false,
)
