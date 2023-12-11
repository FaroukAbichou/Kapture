package recor.video.domain

import probe.domain.WindowPlacement
import probe.domain.model.Screen
import recor.video.domain.model.RecordSettings
import recor.video.domain.model.Video

interface VideoRepository  {
    fun getVideosByPath(filePath: String): List<Video>
    fun recordScreenWithTimeout(
        config: RecordSettings,
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen
    )
    fun startRecording(
        config: RecordSettings,
        windowPlacement: WindowPlacement,
        selectedScreen: Screen
    )
    fun stopRecording()
//    fun saveRecording(outputFilePath: String)
}