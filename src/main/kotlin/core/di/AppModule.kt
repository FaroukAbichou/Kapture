package core.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) = run {
    startKoin {
        appDeclaration()
        modules(recordModules + probModules)
    }
}