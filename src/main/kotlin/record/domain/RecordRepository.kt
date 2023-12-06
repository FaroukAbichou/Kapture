package record.domain

import screen.domain.WindowBounds

interface RecordRepository {
    fun startRecording(config: ConfigurationManager, bounds: WindowBounds?)
    fun stopRecording()
    fun pauseRecording()
    fun resumeRecording(config: ConfigurationManager, bounds: WindowBounds?)
    fun discardRecording()
    fun saveRecording(outputFilePath: String)
    fun setRecordingArea(bounds: WindowBounds)
    fun recordScreen(config: ConfigurationManager, bounds: WindowBounds?)
    fun recordScreenWithAudio(config: ConfigurationManager,bounds: WindowBounds?, audioSource: String)
}
