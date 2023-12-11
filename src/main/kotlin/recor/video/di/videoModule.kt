package recor.video.di

import org.koin.dsl.module
import recor.video.data.VideoRepositoryImpl
import recor.video.domain.VideoRepository

val videoModule = module {
    single<VideoRepository> { VideoRepositoryImpl() }
}