package recor.video.domain

import probe.domain.model.Screen
import probe.domain.WindowBounds
import probe.domain.WindowPlacement
import recor.record.domain.model.ConfigurationManager
import recor.video.domain.model.Video


interface VideoRepository  {
    fun getVideosByPath(filePath: String): List<Video>
    fun recordScreen(config: ConfigurationManager, bounds: WindowBounds?)
    fun recordScreenWithAudio(config: ConfigurationManager, bounds: WindowBounds?, audioSource: String)
    fun startRecording(
        config: ConfigurationManager,
        bounds: WindowBounds?,
        recordingArea: WindowPlacement,
        selectedScreen: Screen
    )

    fun stopRecording()
    fun resumeRecording(config: ConfigurationManager, bounds: WindowBounds?)
    fun saveRecording(outputFilePath: String)
    fun discardRecording()
    fun setRecordingArea(position: WindowPlacement)
    fun pauseRecording()
}