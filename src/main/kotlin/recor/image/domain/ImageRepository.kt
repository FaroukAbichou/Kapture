package recor.image.domain

import recor.image.domain.model.Image

interface ImageRepository {
    fun getImageByPath(filePath: String): List<Image>
}