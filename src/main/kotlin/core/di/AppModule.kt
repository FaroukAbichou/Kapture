package core.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import probe.di.probModule
import recor.audio.di.audioModule
import recor.home.di.homeModule
import recor.image.di.imageModule
import recor.record.di.recordModule
import recor.video.di.videoModule

fun initKoin(
    appDeclaration: KoinAppDeclaration = {}
) = run {
    startKoin {
        appDeclaration()
        modules(
            homeModule,
            videoModule,
            imageModule,
            audioModule,
            recordModule,
            probModule,
        )
    }
}