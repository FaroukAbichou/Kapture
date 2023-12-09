package record.domain

import screen.domain.Screen
import screen.domain.WindowBounds
import screen.domain.WindowPlacement

interface RecordRepository {
    fun startRecording(
        config: ConfigurationManager,
        bounds: WindowBounds?,
        recordingArea: WindowPlacement,
        selectedScreen: Screen
    )
    fun stopRecording()
    fun pauseRecording()
    fun resumeRecording(config: ConfigurationManager, bounds: WindowBounds?)
    fun discardRecording()
    fun saveRecording(outputFilePath: String)
    fun setRecordingArea(position: WindowPlacement)
    fun recordScreen(config: ConfigurationManager, bounds: WindowBounds?)
    fun recordScreenWithAudio(config: ConfigurationManager,bounds: WindowBounds?, audioSource: String)
}
