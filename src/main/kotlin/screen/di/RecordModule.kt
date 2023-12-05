package screen.di

import org.koin.dsl.module
import screen.data.ScreenRepositoryImpl
import screen.domain.ScreenRepository

val screenModule = module {
    single<ScreenRepository> { ScreenRepositoryImpl() }
}