package probe.audiosource.domain.model

import probe.core.Device

data class AudioSource(
    override val id: String,
    override val name : String,
) : Device
