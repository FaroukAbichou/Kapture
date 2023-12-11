package recor.image.di

import org.koin.dsl.module
import recor.image.data.ImageRepositoryImpl
import recor.image.domain.ImageRepository

val imageModule = module {
    single<ImageRepository> { ImageRepositoryImpl() }
}