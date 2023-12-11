package recor.video.data

import recor.image.presentation.state.Image
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.stream.Collectors


class ImageRepository(

) {

    fun getImageByPath(filePath: String): List<Image> {
        val videos = Files.walk(Paths.get(filePath))
            .filter { path -> path.toString().endsWith(".mp4") }
            .collect(Collectors.toList())

        return videos.map { path ->
            Image(
                name = path.fileName.toString(),
                path = path.toString(),
                size = getVideoSize(path).toString(),        // Implement this
                date = getVideoDate(path),        // Implement this
            )
        }
    }

    // Pseudocode for helper functions
    private fun getVideoDuration(path: Path): String {
        val command = arrayOf("/bin/sh", "-c", "ffmpeg -i \"${path.toAbsolutePath()}\" 2>&1 | grep Duration")
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))

        val durationLine = reader.readLine() ?: return "Unknown duration"

        return durationLine.substringAfter("Duration: ").substringBefore(",").trim()
    }

    private fun getVideoSize(path: Path): Long {
        return Files.size(path)
    }

    private fun getVideoDate(path: Path): String {
        val attr = Files.readAttributes(path, BasicFileAttributes::class.java)
        return attr.creationTime().toString()
    }

}