package probe.domain

data class WindowPlacement(
    val width: Int,
    val height: Int,
    val x: Int,
    val y: Int
){
    val x2 = x + width
    val y2 = y + height
    companion object {
        val Default = WindowPlacement(
            width = 200,
            height = 200,
            x = 0,
            y = 0
        )
    }
}