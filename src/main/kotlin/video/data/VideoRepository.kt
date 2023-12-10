package video.data

import video.presentation.state.Video
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors


class VideoRepository(

) {

    fun getVideosByPath(
        filePath: String,
    ): List<Video> {
        val videos =   Files.walk(Paths.get(filePath))
            .map(Path::getFileName)
            .map(Path::toString)
            .filter { n -> n.endsWith(".mp4") }
            .collect(Collectors.toList())

        return videos.map {
            Video(
                name = it,
                path = it,
                duration = it,
                size = it,
                date = it,
                thumbnail = it
            ) }
    }
}