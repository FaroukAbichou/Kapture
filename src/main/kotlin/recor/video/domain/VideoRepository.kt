package recor.video.domain

import probe.domain.WindowBounds
import probe.domain.WindowPlacement
import probe.domain.model.Screen
import recor.video.domain.model.RecordSettings
import recor.video.domain.model.Video

interface VideoRepository  {
    fun getVideosByPath(filePath: String): List<Video>
    fun recordScreenWithTimeout(config: RecordSettings, bounds: WindowBounds?)
    fun startRecording(
        config: RecordSettings,
        bounds: WindowBounds?,
        recordingArea: WindowPlacement,
        selectedScreen: Screen
    )

    fun stopRecording()
    fun resumeRecording(config: RecordSettings, bounds: WindowBounds?)
    fun saveRecording(outputFilePath: String)
    fun discardRecording()
    fun setRecordingArea(position: WindowPlacement)
    fun pauseRecording()
}