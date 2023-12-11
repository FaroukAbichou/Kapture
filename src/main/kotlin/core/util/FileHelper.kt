package core.util

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.stream.Collectors

object FileHelper {
    fun getFilesWithExtension(directoryPath: String, fileExtension: List<String>): List<Path> {
        return Files.walk(Paths.get(directoryPath))
            .filter { fileExtension.any { extension ->
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

}