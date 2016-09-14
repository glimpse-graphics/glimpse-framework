package glimpse.jogl

import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

/**
 * Builds a [GlimpseFrame] initialized with an [init] function.
 */
fun glimpseFrame(title: String, width: Int = 640, height: Int = 480, fps: Int = 30, init: GlimpseFrame.() -> Unit) {
	val frame = GlimpseFrame(title, width, height, fps)
	frame.init()
	frame.start()
}

/**
 * Sets action to be executed when the frame is being closed.
 */
fun JFrame.onClose(callback: () -> Unit) {
	addWindowListener(OnCloseWindowAdapter(callback))
}

private class OnCloseWindowAdapter(val callback: () -> Unit) : WindowAdapter() {
	override fun windowClosing(event: WindowEvent?) {
		callback()
	}
}
