package recor.image.presentation.event

sealed class ImageEvent {
    data class GetImageByPath(val path: String) : ImageEvent()
}