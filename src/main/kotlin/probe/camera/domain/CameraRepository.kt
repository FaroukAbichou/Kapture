package probe.camera.domain

import probe.camera.domain.model.Camera

interface CameraRepository {
    fun getCameras() : List<Camera>
}