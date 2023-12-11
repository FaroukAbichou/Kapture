package probe.domain

data class WindowBounds(
    val x1: Int,
    val y1: Int,
    val x2: Int,
    val y2: Int
) {
    val width: Int get() = x2 - x1
    val height: Int get() = y2 - y1
}