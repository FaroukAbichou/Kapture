package record.video.di

import org.koin.dsl.module
import record.video.data.VideoRepositoryImpl
import record.video.domain.VideoRepository

val videoModule = module {
    single<VideoRepository> { VideoRepositoryImpl() }
}