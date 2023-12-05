package core.di

import home.di.homeModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import record.di.recordModule
import screen.di.screenModule

fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) = run {
    startKoin {
        appDeclaration()
        modules(
            homeModule,
            recordModule,
            screenModule,
        )
    }
}