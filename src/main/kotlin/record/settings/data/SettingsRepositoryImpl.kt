package record.settings.data

import core.util.FilePaths
import record.settings.domain.SettingsRepository

class SettingsRepositoryImpl :SettingsRepository {
    override fun changeOutputLocation(location: String) {
        FilePaths.KapturePath = location
        println(FilePaths.KapturePath)
    }
}