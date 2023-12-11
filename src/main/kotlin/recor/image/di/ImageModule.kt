package recor.image.di

import org.koin.dsl.module
import recor.video.data.ImageRepository

val imageModule = module {
    single<ImageRepository> { ImageRepository() }
}