package record.domain

import screen.domain.WindowBounds

interface RecorderRepository {
    fun startRecording(config: ConfigurationManager, bounds: WindowBounds?)
    fun stopRecording()
    fun pauseRecording()
    fun resumeRecording()
    fun discardRecording()
    fun saveRecording(outputFilePath: String)
    fun setRecordingArea(bounds: WindowBounds)
    fun recordScreen(config: ConfigurationManager, bounds: WindowBounds?)
    fun recordScreenWithAudio(config: ConfigurationManager, audioSource: String)
}
