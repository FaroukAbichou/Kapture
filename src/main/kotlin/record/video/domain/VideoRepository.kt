package record.video.domain

import probe.core.WindowPlacement
import probe.screen.domain.model.Screen
import record.video.domain.model.RecordSettings
import record.video.domain.model.Video
import kotlin.time.Duration

interface VideoRepository  {
    fun getVideosByPath(filePath: String): Result<List<Video>>
    fun recordScreenWithTimeout(
        duration: Duration,
        config: RecordSettings,
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen
    )
    fun startRecording(
        windowPlacement: WindowPlacement?,
        selectedScreen: Screen
    )
    fun stopRecording()

}