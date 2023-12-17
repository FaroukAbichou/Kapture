package record.image.data

import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import record.image.domain.ImageRepository
import record.image.domain.model.Image


class ImageRepositoryImpl : ImageRepository {

    override fun getImageByPath(filePath: String): List<Image> {
        val images = getFilesWithExtension(filePath, listOf(".jpg", ".png", ".jpeg"))

        return images.map { path ->
            Image(
                name = path.fileName.toString(),
                path = path.toString(),
                size = getFileSize(path),
                date = getFileDate(path),
            )
        }
    }


}