package probe.domain

import probe.domain.model.Screen

interface ProbRepository {
    fun getScreens(): List<Screen>
}