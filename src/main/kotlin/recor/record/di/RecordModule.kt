package recor.record.di

import org.koin.dsl.module
import recor.record.data.RecordRepositoryImpl
import recor.record.domain.RecordRepository

val recordModule = module {
    single<RecordRepository> { RecordRepositoryImpl() }
}