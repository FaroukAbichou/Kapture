package probe.domain

import probe.domain.model.AudioSources
import probe.domain.model.Camera
import probe.domain.model.Screen

interface ProbRepository {
    fun getScreens(): List<Screen>
    fun createDirectoriesIfNotExist()
    fun getAudioSources() : List<AudioSources>
    fun getCameras() : List<Camera>
}