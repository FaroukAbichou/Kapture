package recor.audio.di

import org.koin.dsl.module
import recor.video.data.ImageRepository

val audioModule = module {
    single<ImageRepository> { ImageRepository() }
}