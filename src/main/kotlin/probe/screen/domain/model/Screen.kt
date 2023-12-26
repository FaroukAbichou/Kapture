package probe.screen.domain.model

import probe.core.Device

data class Screen(
    override val id: String,
    override val name: String,
    val width : Int,
    val height : Int,
) : Device {
    companion object {
        val defaultScreen = Screen(
            id = "1",
            name = "Screen 0",
            width = 640,
            height = 480
        )
    }
}
