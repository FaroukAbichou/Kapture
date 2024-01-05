package probe.camera.data

import probe.camera.domain.CameraRepository
import probe.camera.domain.model.Camera
import java.util.*


class CameraRepositoryImpl : CameraRepository {
    override fun getCameras(): List<Camera> {
//        val command = listOf("ffmpeg", "-f", "avfoundation", "-list_devices", "true", "-i", "")
//        val process = ProcessBuilder(command).start()
//        process.waitFor()
//
//        val reader = BufferedReader(InputStreamReader(process.errorStream))
//        val output = reader.readText()
//        reader.close()
//
//        val cameraRegex = """\[(\d+)\] (.+)""".toRegex()
//
//        return cameraRegex.findAll(output).mapNotNull { matchResult ->
//            val deviceName = matchResult.groupValues[2]
//
//            if (deviceName.contains("Camera", ignoreCase = true) ||
//                deviceName.contains("Webcam", ignoreCase = true)) {
//                Camera(
//                    id = UUID.randomUUID().toString(),
//                    name = deviceName
//                )
//            } else null
//        }.toList()
        return listOf(Camera(
            id = UUID.randomUUID().toString(),
            name = "Camera"
        ))
    }

}