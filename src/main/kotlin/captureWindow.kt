fun captureWindow(windowId: String, outputFilePath: String) {
    try {
        val processBuilder = ProcessBuilder("/usr/sbin/screencapture", "-l", windowId, outputFilePath)
        val process = processBuilder.start()
        process.waitFor()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun main() {
    val windowId = "Terminal"// Replace with the actual window ID
    val outputFilePath = "imageeeeee.png" // Output file path
    captureWindow(windowId, outputFilePath)
}