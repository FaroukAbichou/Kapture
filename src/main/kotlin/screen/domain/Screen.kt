package screen.domain

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
            width = 1920,
            height = 1080
        )
    }
}
