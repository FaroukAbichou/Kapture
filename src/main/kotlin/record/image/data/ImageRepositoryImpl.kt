package record.image.data

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import core.util.FileHelper.ImageExtensions
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import record.image.domain.ImageRepository
import record.image.domain.model.Image
import java.io.File
import java.nio.file.Path
import javax.imageio.ImageIO

class ImageRepositoryImpl : ImageRepository {

    override fun getImageByPath(filePath: String): Result<List<Image>> {
        val images = getFilesWithExtension(filePath, ImageExtensions)
        return try {
            Result.success(images.map { path ->
                Image(
                    name = path.fileName.toString(),
                    path = path.toString(),
                    size = getFileSize(path),
                    dateCreated = getFileDate(path),
                    thumbnail = getImageThumbnail(path)
                )
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun getImageThumbnail(path: Path): ImageBitmap {
        val originalImage = ImageIO.read(File(path.toString()))
        return originalImage.toComposeImageBitmap()
    }

}