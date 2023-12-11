package probe.domain

interface ScreenRepository {
    fun getScreens(): List<Screen>
}