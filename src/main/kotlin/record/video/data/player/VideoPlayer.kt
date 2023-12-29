package record.video.data.player

import com.sun.jna.Native
import com.sun.jna.NativeLibrary
import uk.co.caprica.vlcj.binding.lib.LibVlc
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import java.awt.Toolkit
import javax.swing.JFrame
import javax.swing.SwingUtilities

class VLCPlayer private constructor() {
    private val mediaPlayerComponent: EmbeddedMediaPlayerComponent

    init {

    //MAXIMIZE TO SCREEN
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        val frame = JFrame()
        mediaPlayerComponent = EmbeddedMediaPlayerComponent()
        frame.contentPane = mediaPlayerComponent
        frame.setLocation(0, 0)
        frame.setSize(300, 400)
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
        frame.isVisible = true
        mediaPlayerComponent.mediaPlayer()
    }

    companion object {
        //This is the path for libvlc.dll
        @JvmStatic
        fun main(args: Array<String>) {
            NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC")
            Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc::class.java)
            SwingUtilities.invokeLater { val vlcPlayer = VLCPlayer() }
        }
    }
}