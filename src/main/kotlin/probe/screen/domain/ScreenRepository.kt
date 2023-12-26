package probe.screen.domain

import probe.screen.domain.model.Screen

interface ScreenRepository {
    fun getScreens(): List<Screen>
    fun createDirectoriesIfNotExist()
}