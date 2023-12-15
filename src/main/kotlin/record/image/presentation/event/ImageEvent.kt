package record.image.presentation.event

sealed class ImageEvent {
    data object GetImages : ImageEvent()
    data class SelectImage(val path: String) : ImageEvent()
    data class DeleteImage(val path: String) : ImageEvent()
    data class GetImageByPath(val path: String) : ImageEvent()
    data class SelectImagesLocation(val path: String) : ImageEvent()
}