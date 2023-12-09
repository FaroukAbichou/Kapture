package record.presentation.state

import core.ImageResource

data class RecordState(
    val isLoading: Boolean = false,
    val records: List<Record> = emptyList(),
    val error: Throwable? = null,
    val recordTypes : List<RecordType> = listOf(
        RecordType(
            id = 1,
            name = "Select Recording Area",
            icon = ImageResource.crop,
            description = "Select Recording Area",
        ),
        RecordType(
            id = 2,
            name = "Full Screen",
            description = "Full Screen",
            icon = ImageResource.display,
        ),
        RecordType(
            id = 3,
            name = "Specific Window",
            description = "Specific Window",
            icon = ImageResource.lockDisplay,
        ),
        RecordType(
            id = 4,
            name = "Device Recording",
            description = "Device Recording",
            icon = ImageResource.cameraLens,
        ),
        RecordType(
            id = 5,
            name = "Audio only",
            description = "Audio only",
            icon = ImageResource.waveformCircle,
        ),
        RecordType(
            id = 6,
            name = "All Windows",
            description = "All Windows",
            icon = ImageResource.multipleDisplay,
        ),
    ),
)

