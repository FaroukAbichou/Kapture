package record.video.di

import org.koin.dsl.module
import record.video.data.VideoRepositoryImpl
import record.video.data.player.VideoPlayer
import record.video.domain.VideoRepository

val videoModule = module {
    single<VideoPlayer> { VideoPlayer() }
    single<VideoRepository> { VideoRepositoryImpl() }
}