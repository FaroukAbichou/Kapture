package recor.image.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import recor.image.domain.ImageRepository
import recor.image.presentation.event.ImageEvent
import recor.image.presentation.state.ImageState

class ImageViewModel : KoinComponent {

    private val imageRepository: ImageRepository by inject()

    private val _state = MutableStateFlow(ImageState())
    val state: StateFlow<ImageState> = _state.asStateFlow()

    fun onEvent(event: ImageEvent) {
        when (event) {
            is ImageEvent.GetImageByPath -> {
                _state.value = _state.value.copy(
                    images = imageRepository.getImageByPath(event.path)
                )
            }
        }
    }
}