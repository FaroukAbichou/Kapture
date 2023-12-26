package probe.screen.di

import org.koin.dsl.module
import probe.screen.data.ScreenRepositoryImpl
import probe.screen.domain.ScreenRepository

val screenModule = module {
    single<ScreenRepository> { ScreenRepositoryImpl() }
}