package record.audio.di

import org.koin.dsl.module
import record.audio.domain.AudioRepository
import record.audio.data.AudioRepositoryImpl

val audioModule = module {
    single<AudioRepository> { AudioRepositoryImpl() }
}