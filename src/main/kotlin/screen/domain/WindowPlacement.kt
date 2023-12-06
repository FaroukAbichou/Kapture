package screen.domain

data class WindowPlacement(
    val width: Int,
    val height: Int,
    val x: Int,
    val y: Int
){
    companion object {
        val Default = WindowPlacement(
            width = 200,
            height = 200,
            x = 0,
            y = 0
        )

        fun fromBounds(bounds: WindowBounds): WindowPlacement {
            return WindowPlacement(
                width = bounds.width,
                height = bounds.height,
                x = bounds.x1,
                y = bounds.y1
            )
        }
    }
}