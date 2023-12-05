interface IScreenRecorder {
    fun startRecording(config: ConfigurationManager, bounds: WindowBounds?)
    fun stopRecording()
    fun pauseRecording()
    fun resumeRecording()
    fun saveRecording(outputFilePath: String)
    fun discardRecording()
    fun setRecordingArea(bounds: WindowBounds)
    fun recordScreen(config: ConfigurationManager, bounds: WindowBounds?)
    fun recordScreenWithAudio(config: ConfigurationManager, audioSource: String)
}
