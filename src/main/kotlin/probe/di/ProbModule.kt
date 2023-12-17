package probe.di

import org.koin.dsl.module
import probe.data.ProbRepositoryImpl
import probe.domain.ProbRepository

val probModule = module {
    single<ProbRepository> { ProbRepositoryImpl() }
}