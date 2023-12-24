package probe.domain.model

data class AudioSource(
    override val id: String,
    override val name : String,
) : Device
