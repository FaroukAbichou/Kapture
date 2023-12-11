package recor.audio.di

import org.koin.dsl.module
import recor.audio.data.AudioRepository
import recor.audio.domain.AudioRepositoryImpl

val audioModule = module {
    single<AudioRepository> { AudioRepositoryImpl() }
}