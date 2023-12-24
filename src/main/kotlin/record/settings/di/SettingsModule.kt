package record.settings.di

import org.koin.dsl.module
import record.settings.data.SettingsRepositoryImpl
import record.settings.domain.SettingsRepository

val settingsModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl() }
}