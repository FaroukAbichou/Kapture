//package record.video.data;
//
//import org.monte.media.av.Format;
//import org.monte.media.math.Rational;
//import org.monte.media.screenrecorder.ScreenRecorder;
//
//import java.awt.*;
//import java.io.File;
//import java.io.IOException;
//import java.time.Duration;
//
//import static org.monte.media.av.FormatKeys.*;
//import static org.monte.media.av.codec.video.VideoFormatKeys.*;
//
//public class Screencorder {
//    private ScreenRecorder screenRecorder;
//    public final String movieFolder;
//
//    public void start() throws IOException, AWTException {
//        GraphicsConfiguration gc = GraphicsEnvironment
//                .getLocalGraphicsEnvironment()
//                .getDefaultScreenDevice()
//                .getDefaultConfiguration();
//
//        Format fileFormat = new Format(MediaTypeKey, MediaType.FILE,
//                MimeTypeKey, MIME_AVI);
//        Format screenFormat = new Format(MediaTypeKey, MediaType.VIDEO,
//                EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//                CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
//                DepthKey, 24,
//                FrameRateKey, Rational.valueOf(15),
//                QualityKey, 1.0f,
//                KeyFrameIntervalKey, (15 * 60));
//        Format mouseFormat = new Format(MediaTypeKey, MediaType.VIDEO,
//                EncodingKey, ScreenRecorder.ENCODING_BLACK_CURSOR,
//                FrameRateKey, Rational.valueOf(30));
//
//        screenRecorder = new ScreenRecorder(gc, null,
//                fileFormat, screenFormat, mouseFormat,
//                null, new File(movieFolder));
//
//        screenRecorder.start();
//    }
//
//    public void stop() throws IOException {
//        screenRecorder.stop();
//    }
//
//    public Screencorder() {
//        if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
//            movieFolder = System.getProperty("user.home") + File.separator + "Videos";
//        } else {
//            movieFolder = System.getProperty("user.home") + File.separator + "Movies";
//        }
//
//        File movieDir = new File(movieFolder);
//        if (!movieDir.exists()) {
//            movieDir.mkdirs();
//        }
//    }
//
//    public String getMovieFolder() {
//        return movieFolder;
//    }
//}
