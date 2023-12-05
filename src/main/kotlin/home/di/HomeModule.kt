package home.di

import org.koin.dsl.module
import record.data.RecorderRepositoryImpl
import record.domain.RecorderRepository
import screen.data.ScreenInfo

val homeModule = module {
    single<ScreenInfo> { ScreenInfo() }
    single<RecorderRepository> { RecorderRepositoryImpl(get()) }
}