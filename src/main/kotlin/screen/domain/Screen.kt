package screen.domain

data class Screen(
    val id: String,
    val name: String,
    val bounds: WindowBounds
){
    companion object {
        val defaultScreen = Screen(
            id = "0",
            name = "Screen 0",
            bounds = WindowBounds(
                x1 = 0,
                y1 = 0,
                x2 = 1920,
                y2 = 1080,
            ),
        )
    }
}
