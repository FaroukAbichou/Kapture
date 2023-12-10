package video.di

import org.koin.dsl.module
import video.data.VideoRepository

val videoModule = module {
    single<VideoRepository> { VideoRepository() }
}