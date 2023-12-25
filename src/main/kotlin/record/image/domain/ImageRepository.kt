package record.image.domain

import record.image.domain.model.Image

interface ImageRepository {
    fun getImageByPath(filePath: String): Result<List<Image>>
}