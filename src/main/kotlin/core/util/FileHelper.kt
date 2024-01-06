package core.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.stream.Collectors

object FileHelper {

    val VideoExtensions = setOf(
        ".mp4", ".mkv", ".avi", ".mov", ".webm", ".flv", ".wmv",
        ".m4v", ".mpg", ".mpeg", ".m2v", ".3gp", ".3g2"
    )
    val ImageExtensions = setOf(
        ".png", ".jpg", ".jpeg", ".bmp",
        ".gif", ".webp", ".tiff", ".tif"
    )
    val AudioExtensions = setOf(
        ".mp3", ".wav", ".wma", ".m4a", ".aac", ".flac", ".ogg",
        ".opus", ".amr", ".mka", ".m4b", ".m4p", ".mpc", ".ra",
        ".rm", ".tta", ".wv", ".wv", ".aiff", ".ape", ".dts",
        ".dsf", ".dff", ".m4r", ".mid", ".mka", ".mp2", ".mpa",
        ".mpc", ".ofr", ".oga", ".spx", ".vqf", ".w64", ".wma",
        ".wv", ".xm"
    )
    fun getFilesWithExtension(directoryPath: String, fileExtension: Set<String>): List<Path> {
        return Files.walk(Paths.get(directoryPath))
            .filter {
                fileExtension.any { extension ->
                    it.fileName.toString().endsWith(extension)
                }
            }
            .collect(Collectors.toList())
            .toList()
    }
    fun getFilesWithExtension(directoryPath: String, fileExtension: String): List<Path> {
        return Files.walk(Paths.get(directoryPath))
            .filter { it.fileName.toString().endsWith(fileExtension) }
            .collect(Collectors.toList())
            .toList()
    }

    fun getFileDate(path: Path): String {
        val attr = Files.readAttributes(path, BasicFileAttributes::class.java)
        return attr.creationTime().toString()
    }
    fun getFileSize(path: Path): Long {
        return Files.size(path)
    }
    fun getFileSize(path: String): Long {
        return Files.size(Paths.get(path))
    }

    fun getRecordingOutputFileName(date: Instant): String {
        val localDateTime = date.toLocalDateTime(TimeZone.UTC)

        val formattedDate = "${localDateTime.year}-" +
                localDateTime.monthNumber.toString().padStart(2, '0') + "-" +
                localDateTime.dayOfMonth.toString().padStart(2, '0')

        val hour = if (localDateTime.hour > 12) localDateTime.hour - 12 else if (localDateTime.hour == 0) 12 else localDateTime.hour
        val amPm = if (localDateTime.hour < 12) "AM" else "PM"
        val formattedTime = "${hour.toString().padStart(2, '0')}." +
                localDateTime.minute.toString().padStart(2, '0') + "." +
                localDateTime.second.toString().padStart(2, '0') +
                " $amPm"

        return "Screen Recording $formattedDate at $formattedTime"
    }

    fun getRecordingOutputFileName(): String {
        return getRecordingOutputFileName(Clock.System.now())
    }
}