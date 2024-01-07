package probe.camera.data

import probe.camera.domain.CameraRepository
import probe.camera.domain.model.Camera


class CameraRepositoryImpl : CameraRepository {
    override fun getCameras(): List<Camera> {
        return listOf(Camera("1", "Camera 1"))
    }

}