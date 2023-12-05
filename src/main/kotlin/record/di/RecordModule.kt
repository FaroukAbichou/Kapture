package record.di

import org.koin.dsl.module
import record.data.RecordRepositoryImpl
import record.domain.RecordRepository

val recordModule = module {
    single<RecordRepository> { RecordRepositoryImpl() }
}