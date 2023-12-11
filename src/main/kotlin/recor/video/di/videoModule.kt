package recor.video.di

import org.koin.dsl.module
import recor.video.data.ImageRepository

val videoModule = module {
    single<ImageRepository> { ImageRepository() }
}