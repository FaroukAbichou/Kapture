package record.di

import org.koin.dsl.module
import record.data.RecorderRepositoryImpl
import record.domain.RecorderRepository

val recordModule = module {
    single<RecorderRepository> { RecorderRepositoryImpl() }
}