package probe.camera.domain.model

import probe.core.Device

data class Camera(
    override val id: String,
    override val name: String,
) : Device