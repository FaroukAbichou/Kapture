package recor.audio.di

import org.koin.dsl.module
import recor.audio.domain.AudioRepository
import recor.audio.data.AudioRepositoryImpl

val audioModule = module {
    single<AudioRepository> { AudioRepositoryImpl() }
}