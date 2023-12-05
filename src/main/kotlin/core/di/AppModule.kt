package core.di

import home.di.homeModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) = run {
    startKoin {
        appDeclaration()
        modules(
            homeModule,
        )
    }
}