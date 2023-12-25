package record.image.presentation

import core.util.FilePaths
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import record.home.presentation.event.RecordingFrameEvent
import record.image.domain.ImageRepository
import record.image.presentation.event.ImageEvent
import record.image.presentation.state.ImageState

class ImageViewModel : KoinComponent {

    private val imageRepository: ImageRepository by inject()

    private val _state = MutableStateFlow(ImageState())
    val state: StateFlow<ImageState> = _state.asStateFlow()

    val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        getImagesByPath(FilePaths.ImagesPath)
    }

    fun onEvent(event: ImageEvent) {
        when (event) {
            is ImageEvent.GetImageByPath -> {
                getImagesByPath(event.path)
            }

            is ImageEvent.DeleteImage -> {

            }

            ImageEvent.GetImages -> {

            }

            is ImageEvent.SelectImage -> {

            }

            is ImageEvent.SelectImagesLocation -> {

            }
        }
    }

    fun onRecordingFrameEvent(updateWindowPlacement: RecordingFrameEvent) {
        when (updateWindowPlacement) {
            is RecordingFrameEvent.UpdateWindowPlacement -> {

            }
        }
    }

    private fun getImagesByPath(path: String) {
        _state.value = _state.value.copy(
            isLoading = true
        )
        coroutineScope.launch {
            imageRepository.getImageByPath(path)
                .onSuccess {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        images = it
                    )
                }.onFailure {
                    _state.value = _state.value.copy(
                        isLoading = false
                    )
                }
        }

    }
}