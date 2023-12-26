package probe.audiosource.di

import org.koin.dsl.module
import probe.audiosource.data.AudioSourceRepositoryImpl
import probe.audiosource.domain.AudioSourceRepository

val audioSourceModule = module {
    single<AudioSourceRepository> { AudioSourceRepositoryImpl() }
}