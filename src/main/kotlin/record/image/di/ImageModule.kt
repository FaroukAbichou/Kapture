package record.image.di

import org.koin.dsl.module
import record.image.data.ImageRepositoryImpl
import record.image.domain.ImageRepository

val imageModule = module {
    single<ImageRepository> { ImageRepositoryImpl() }
}