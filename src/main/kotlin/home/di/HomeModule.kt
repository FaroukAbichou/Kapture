package home.di

import org.koin.dsl.module
import record.data.RecorderRepositoryImpl
import record.domain.RecorderRepository

val homeModule = module {
    single<RecorderRepository> { RecorderRepositoryImpl() }
}