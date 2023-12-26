package core.di

import probe.audiosource.di.audioSourceModule
import probe.camera.di.cameraModule
import probe.screen.di.screenModule

val probModules = listOf(
    audioSourceModule,
    cameraModule,
    screenModule
)