package probe.domain

data class Screen(
    val id: String,
    val name: String,
    val width : Int,
    val height : Int,
){
    companion object {
        val defaultScreen = Screen(
            id = "0",
            name = "Screen 0",
            width = 640,
            height = 480
        )
    }
}
