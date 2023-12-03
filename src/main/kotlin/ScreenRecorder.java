import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

public class ScreenRecorder {
    public static void main(String[] args) throws Exception {
        String outputFile = "output.mp4";

        // Set up the grabber
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(":0.0+10,20"); // For Unix systems
        grabber.setFormat("x11grab");
        grabber.setImageWidth(1280);
        grabber.setImageHeight(720);
        grabber.setFrameRate(30);

        // Set up the recorder
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, grabber.getImageWidth(), grabber.getImageHeight());
        recorder.setVideoCodecName("libx264");
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setPixelFormat(0); // pixelFormat

        // Start recording
        grabber.start();
        recorder.start();

        Frame capturedFrame;
        // Record for 10 seconds for the example
        long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis() - startTime) < (10 * 1000)) {
            capturedFrame = grabber.grab();
            if (capturedFrame != null) {
                recorder.record(capturedFrame);
            }
        }

        // Stop and release the resources
        recorder.stop();
        recorder.release();
        grabber.stop();
        grabber.release();
    }
}
