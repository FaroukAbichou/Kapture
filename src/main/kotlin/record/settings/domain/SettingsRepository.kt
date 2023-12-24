package record.settings.domain

interface SettingsRepository {
    fun changeOutputLocation(location:String)

}