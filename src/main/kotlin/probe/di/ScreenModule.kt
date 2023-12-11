package probe.di

import org.koin.dsl.module
import probe.data.ScreenRepositoryImpl
import probe.domain.ScreenRepository

val screenModule = module {
    single<ScreenRepository> { ScreenRepositoryImpl() }
}