package screen.domain

interface ScreenRepository {
    fun getScreenList(): List<Screen>
    fun getScreen(screenId: String): Screen
}