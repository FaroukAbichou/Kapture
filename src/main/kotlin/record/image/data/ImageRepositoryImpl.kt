package record.image.data

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import core.util.FileHelper.ImageExtensions
import core.util.FileHelper.getFileDate
import core.util.FileHelper.getFileSize
import core.util.FileHelper.getFilesWithExtension
import record.image.domain.ImageRepository
import record.image.domain.model.Image
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Path
import javax.imageio.ImageIO

class ImageRepositoryImpl : ImageRepository {

    override fun getImageByPath(filePath: String): List<Image> {
        val images = getFilesWithExtension(filePath, ImageExtensions)

        return images.map { path ->
            Image(
                name = path.fileName.toString(),
                path = path.toString(),
                size = getFileSize(path),
                dateCreated = getFileDate(path),
                thumbnail = getImageThumbnail(path)
            )
        }
    }

    private fun getImageThumbnail(path: Path, thumbnailSize: Int = 400): ImageBitmap {
        val originalImage = ImageIO.read(File(path.toString()))
        val resizedImage = resizeImage(originalImage, thumbnailSize, thumbnailSize)
        return resizedImage.toComposeImageBitmap()
    }

    private fun resizeImage(originalImage: BufferedImage, width: Int, height: Int): BufferedImage {
        val resizedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val graphics2D = resizedImage.createGraphics()
        graphics2D.drawImage(
            originalImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH),
            0,
            0,
            null
        )
        graphics2D.dispose()
        return resizedImage
    }
}