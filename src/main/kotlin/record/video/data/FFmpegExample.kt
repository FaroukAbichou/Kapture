package record.video.data

import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FFmpegFrameRecorder


object FFmpegExample {
    @JvmStatic
    fun main(args: Array<String>) {
        val inputPath = "input.mp4" // Path to input video
        val outputPath = "output.avi" // Path for output video
        try {
            val grabber = FFmpegFrameGrabber(inputPath)
            grabber.start()
            val recorder = FFmpegFrameRecorder(
                outputPath,
                grabber.getImageWidth(),
                grabber.getImageHeight(),
                grabber.getAudioChannels()
            )
            recorder.setFormat("avi") // Set output format
            recorder.setFrameRate(grabber.getFrameRate())
            recorder.setSampleRate(grabber.getSampleRate())
            recorder.start()
            while (true) {
                val frame: org.bytedeco.javacv.Frame = grabber.grab() ?: break
                recorder.record(frame)
            }
            recorder.stop()
            recorder.release()
            grabber.stop()
            grabber.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

