package screen.data;

import java.io.IOException;

public class permission {
    public void requestScreenRecordingPermission() {
        try {
            String script = "tell application \"System Preferences\"\n"
                    + "reveal anchor \"Privacy_ScreenCapture\" of pane id \"com.apple.preference.security\"\n"
                    + "activate\n"
                    + "end tell";
            String[] command = {"osascript", "-e", script};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

}
