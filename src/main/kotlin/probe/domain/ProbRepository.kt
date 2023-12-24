package probe.domain

import probe.domain.model.AudioSource
import probe.domain.model.Camera
import probe.domain.model.Screen

interface ProbRepository {
    fun getScreens(): List<Screen>
    fun createDirectoriesIfNotExist()
    fun getAudioSources() : List<AudioSource>
    fun getCameras() : List<Camera>
}