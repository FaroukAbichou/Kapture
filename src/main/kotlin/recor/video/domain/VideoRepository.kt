package recor.video.domain

import probe.domain.Screen
import probe.domain.WindowBounds
import probe.domain.WindowPlacement
import recor.record.domain.ConfigurationManager
import recor.video.presentation.state.Video


interface VideoRepository  {
    fun getVideosByPath(path: String): List<Video>

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