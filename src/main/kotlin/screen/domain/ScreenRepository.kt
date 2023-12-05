package screen.domain

interface ScreenRepository {
    fun getScreens(): List<Screen>
}