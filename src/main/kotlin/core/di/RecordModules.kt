package core.di

import recor.audio.di.audioModule
import recor.home.di.homeModule
import recor.image.di.imageModule
import recor.video.di.videoModule

val recordModules = listOf(
    homeModule,
    videoModule,
    imageModule,
    audioModule
)