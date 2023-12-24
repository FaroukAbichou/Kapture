package probe.domain.model

data class Camera(
    override val id: String,
    override val name: String,
) : Device