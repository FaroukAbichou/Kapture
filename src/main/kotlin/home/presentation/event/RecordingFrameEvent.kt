package home.presentation.event

sealed class RecordingFrameEvent {

    data class UpdateWindowPlacement(
        val x: Int,
        val  y: Int,
        val width: Int,
        val height: Int
    ) : RecordingFrameEvent()

}