package core.util


object FilePaths {
    var KapturePath = "${System.getProperty("user.home")}/Kapture"
    val VideosPath = "$KapturePath/Videos"
    val AudiosPath = "$KapturePath/Audios"
    val ImagesPath = "$KapturePath/Pictures"
}