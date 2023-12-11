package probe.domain

import probe.domain.model.Screen

interface ScreenRepository {
    fun getScreens(): List<Screen>
}